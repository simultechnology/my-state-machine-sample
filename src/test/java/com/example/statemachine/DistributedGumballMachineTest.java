package com.example.statemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DistributedGumballMachineTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;
    
    @Mock
    private RedissonClient redisson;
    
    @Mock
    private RLock lock;
    
    @Mock
    private ValueOperations<String, String> valueOps;
    
    private DistributedGumballMachine machine;
    
    @BeforeEach
    void setUp() {
        machine = new DistributedGumballMachine(redisTemplate, redisson);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(redisson.getLock(anyString())).thenReturn(lock);
    }
    
    @Test
    void insertQuarter_Success() throws InterruptedException {
        String sessionId = "test-session";
        when(valueOps.get("state:" + sessionId)).thenReturn(VendingMachineState.NO_QUARTER.name());
        when(lock.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(true);
        
        machine.insertQuarter(sessionId);
        
        verify(valueOps).set("state:" + sessionId, VendingMachineState.HAS_QUARTER.name());
        verify(lock).unlock();
    }
    
    @Test
    void insertQuarter_AlreadyHasQuarter() throws InterruptedException {
        String sessionId = "test-session";
        when(valueOps.get("state:" + sessionId)).thenReturn(VendingMachineState.HAS_QUARTER.name());
        when(lock.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(true);
        
        assertThrows(IllegalStateException.class, () -> machine.insertQuarter(sessionId));
        verify(lock).unlock();
    }
    
    @Test
    void insertQuarter_LockFailure() throws InterruptedException {
        when(lock.tryLock(10, 30, TimeUnit.SECONDS)).thenReturn(false);
        
        assertThrows(RuntimeException.class, () -> machine.insertQuarter("test-session"));
        verify(lock, never()).unlock();
    }
}