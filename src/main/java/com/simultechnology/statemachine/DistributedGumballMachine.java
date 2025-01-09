package com.simultechnology.statemachine;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistributedGumballMachine {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedissonClient redisson;

    @Transactional
    public void insertQuarter(String sessionId) {
        RLock lock = redisson.getLock("gumball:lock:" + sessionId);
        try {
            if (!lock.tryLock(10, 30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Could not acquire lock");
            }
            try {
                VendingMachineState currentState = getState(sessionId);
                if (currentState == VendingMachineState.NO_QUARTER) {
                    setState(sessionId, VendingMachineState.HAS_QUARTER);
                } else {
                    throw new IllegalStateException("Invalid state for inserting quarter: " + currentState);
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to acquire lock", e);
        }
    }

    private VendingMachineState getState(String sessionId) {
        String state = redisTemplate.opsForValue().get("state:" + sessionId);
        return state != null ? VendingMachineState.valueOf(state) : VendingMachineState.NO_QUARTER;
    }

    private void setState(String sessionId, VendingMachineState state) {
        redisTemplate.opsForValue().set("state:" + sessionId, state.name());
    }
}
