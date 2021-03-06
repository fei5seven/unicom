package me.kuku.scheduled;

import me.kuku.entity.PhoneLa;
import me.kuku.entity.Prize;
import me.kuku.repository.PhoneRepository;
import me.kuku.repository.PrizeRepository;
import me.kuku.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@EnableScheduling
public class LotteryScheduled {

    @Autowired
    LotteryService lotteryService;
    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PrizeRepository prizeRepository;

    @Scheduled(cron = "0 1 0 * * ?")
    public void flow() throws Exception{
        List<PhoneLa> phoneAll = phoneRepository.findAll();
        String lotteryStr = "", phone = "";
        for (PhoneLa phoneLa : phoneAll){
            lotteryStr = "";
            phone = phoneLa.getPhone();
            for (int i = 0; i < 3; i++) {
                String prize = lotteryService.run(phone);
                lotteryStr += prize + "；";
            }
            prizeRepository.save(new Prize(null, phone, lotteryStr, new Date(new java.util.Date().getTime())));
        }
    }
}
