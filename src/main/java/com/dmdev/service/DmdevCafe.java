package com.dmdev.service;

import com.dmdev.job.BuyerStartsJob;
import com.dmdev.model.Order;
import com.dmdev.util.CafeConst;

import javax.swing.plaf.TableHeaderUI;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static com.dmdev.util.CafeConst.*;

public class DmdevCafe extends Thread{

    private final ScheduledExecutorService executorService;
    private  final List<Buyer> buyers;
    private final List<Cashbox> cashboxes;
    private final BlockingQueue<Order> allOrders;

    public DmdevCafe(int buyersNumber, int cashboxesNumber){
        this.executorService = Executors.newScheduledThreadPool(3);
        this.allOrders = new ArrayBlockingQueue<>(cashboxesNumber * 10);
        this.buyers = createBuyers(buyersNumber);
        this.cashboxes = createCashBoxes(cashboxesNumber);
    }

    @Override
    public void run() {
        buyers.forEach(buyer -> new Thread(buyer).start());
        cashboxes.forEach(cashbox -> new Thread(cashbox).start());

        var buyerStatsPath= Path.of("resourses","buyers-stats.csv");
        executorService.scheduleAtFixedRate(new BuyerStartsJob(buyers,buyerStatsPath), BUYER_STATS_JOB_PERIOD, BUYER_STATS_JOB_PERIOD, TimeUnit.SECONDS);

        var cashboxStatsPath =Path.of("resources","cashboxes-stats.csv");
        executorService.scheduleAtFixedRate(new CashboxStatsJob(cashboxes,cashboxStatsPath), CASHBOX_STATS_JOB_PERIOD, CASHBOX_STATS_JOB_PERIOD, TimeUnit.SECONDS);


        executorService.scheduleAtFixedRate(new WinnerJob(buyerStatsPath,cashboxStatsPath), WINNER_JOB_PERIOD, WINNER_JOB_PERIOD, TimeUnit.SECONDS);



    }

    private List<Buyer> createBuyers(int buyersNumber){
        return IntStream.range(0, buyersNumber)
                .mapToObj(i -> new Buyer(allOrders))
                .toList();
    }
    private List<Cashbox> createCashBoxes(int cashboxesNumber){
        return IntStream.range(0, cashboxesNumber)
                .mapToObj(i -> new Cashbox(allOrders))
                .toList();
    }





}
