package com.gustavotorres.cadastropessoa.services;

import java.util.Random;

public class ScoreService {
    
    public static Short gerarScoreRandomico() {
        return (short) new Random().nextInt(10);
    }
}
