package com.test.shuffle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ShuffleServiceImpl implements ShuffleService {

    public static final int INITIAL_VALUE = 1;

    public static final int MAX_VALUE = 1000;

    private final Random random;

    @Autowired
    public ShuffleServiceImpl(Random random) {
        this.random = random;
    }

    @Override
    public List<Integer> shuffleArray(int number) {
        if (number < INITIAL_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Number must be between 1 and 1000");
        }

        List<Integer> numbers = IntStream.rangeClosed(INITIAL_VALUE, number).boxed().collect(Collectors.toList());
        fisherYatesShuffle(numbers);
        return numbers;
    }

    private void fisherYatesShuffle(List<Integer> number) {
        for (int i = number.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = number.get(i);
            number.set(i, number.get(j));
            number.set(j, temp);
        }
    }
}
