package com.DemoProject.Trimble_Cars;

import com.DemoProject.Trimble_Cars.model.*;
import com.DemoProject.Trimble_Cars.repository.LeaseRepository;
import com.DemoProject.Trimble_Cars.services.CarService;
import com.DemoProject.Trimble_Cars.services.LeaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaseServiceTest {

    @InjectMocks
    private LeaseService leaseService;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private CarService carService;

    private User customer;
    private Car car;
    private Lease lease;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new User();
        customer.setId(1L);
        customer.setUsername("Test User");

        car = new Car();
        car.setId(101L);
        car.setModel("BMW");
        car.setRegistrationNumber("TN09AB1234");
        car.setStatus(CarStatus.IDLE);
        car.setOwner(customer);

        lease = new Lease();
        lease.setId(1L);
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now().minusDays(5));
    }

    @Test
    void testGetAllLeases() {
        when(leaseRepository.findAll()).thenReturn(List.of(lease));
        List<Lease> leases = leaseService.getAllLeases();
        assertThat(leases).hasSize(1).contains(lease);
        verify(leaseRepository).findAll();
    }

    @Test
    void testStartLease_WhenAllowed() {
        when(leaseRepository.findByCustomer(customer)).thenReturn(List.of()); // 0 existing leases
        when(leaseRepository.save(any(Lease.class))).thenAnswer(inv -> inv.getArgument(0));

        Lease result = leaseService.startLease(car, customer);

        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getCar()).isEqualTo(car);
        assertThat(result.getStartDate()).isNotNull();
        verify(carService).updateCarStatus(car.getId(), CarStatus.ON_LEASE);
        verify(leaseRepository).save(any(Lease.class));
    }

    @Test
    void testStartLease_WhenLimitExceeded() {
        when(leaseRepository.findByCustomer(customer)).thenReturn(List.of(lease, lease)); // 2 existing leases

        RuntimeException exception = catchThrowableOfType(
                () -> leaseService.startLease(car, customer),
                RuntimeException.class
        );

        assertThat(exception).hasMessage("Maximum 2 leases allowed");
        verify(carService, never()).updateCarStatus(anyLong(), any());
        verify(leaseRepository, never()).save(any());
    }

    @Test
    void testEndLease() {
        lease.setEndDate(null);
        when(leaseRepository.save(any(Lease.class))).thenAnswer(inv -> inv.getArgument(0));

        Lease result = leaseService.endLease(lease);

        assertThat(result.getEndDate()).isNotNull();
        verify(carService).updateCarStatus(lease.getCar().getId(), CarStatus.IDLE);
        verify(leaseRepository).save(lease);
    }

    @Test
    void testGetLeaseHistory() {
        when(leaseRepository.findByCustomer(customer)).thenReturn(List.of(lease));
        List<Lease> history = leaseService.getLeaseHistory(customer);
        assertThat(history).hasSize(1).contains(lease);
        verify(leaseRepository).findByCustomer(customer);
    }
}
