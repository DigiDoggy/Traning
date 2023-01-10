package com.dmdev.job;

import com.dmdev.mapper.BuyerMapper;
import com.dmdev.service.Buyer;

import java.nio.file.Path;
import java.util.List;

public class BuyerStartsJob implements Runnable {


    private final BuyerMapper buyerMapper = new BuyerMapper();
    private final List<Buyer> buyers;
    private final Path buyerStatsPath;

    public BuyerStartsJob(List<Buyer> buyers, Path buyerStatsPath) {
        this.buyers = buyers;
        this.buyerStatsPath = buyerStatsPath;
    }

    @Override
    public void run() {
        buyers.stream()
                .map(buyerMapper::map)
                .map(buyerRow -> )
                .toList();
    }




}
