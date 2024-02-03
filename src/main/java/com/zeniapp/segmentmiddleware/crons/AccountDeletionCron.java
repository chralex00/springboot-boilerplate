package com.zeniapp.segmentmiddleware.crons;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.zeniapp.segmentmiddleware.configs.Configs;
import com.zeniapp.segmentmiddleware.dtos.AccountQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.DietQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.TdeeQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.TrainingQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.entities.Diet;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.entities.Tdee;
import com.zeniapp.segmentmiddleware.entities.Training;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.DietService;
import com.zeniapp.segmentmiddleware.services.SessionService;
import com.zeniapp.segmentmiddleware.services.TdeeService;
import com.zeniapp.segmentmiddleware.services.TrainingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccountDeletionCron {
    @Autowired
    private Configs configs;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private TdeeService tdeeService;
    
    @Autowired
    private DietService dietService;
    
    @Autowired
    private TrainingService trainingService;
    
    @Scheduled(cron = "${crons.account-deletion.cron-expression}")
    public void accountDeletionHandler() {
        try {
            AccountDeletionCron.log.info("AccountDeletionCron started");

            AccountQueryParamsDto accountQueryParamsDto = new AccountQueryParamsDto();
            accountQueryParamsDto.setOffset(0);
            accountQueryParamsDto.setLimit(1);
            accountQueryParamsDto.setSortField("createdOn");
            accountQueryParamsDto.setSortDirection("asc");
            accountQueryParamsDto.setIsDeleted(true);
            List<Account> accounts = this.accountService.findMany(accountQueryParamsDto);

            if (accounts == null || accounts.isEmpty()) {
                AccountDeletionCron.log.info("AccountDeletionCron finished with success: no accounts to delete");
                return;
            }

            Integer deletionLimit = this.configs.getCronsAccountDeletionDataStructureToDeleteMaxLimit();
            String accountId = accounts.get(0).getId();
            String username = accounts.get(0).getUsername();

            TdeeQueryParamsDto tdeeQueryParamsDto = new TdeeQueryParamsDto();
            tdeeQueryParamsDto.setOffset(0);
            tdeeQueryParamsDto.setLimit(deletionLimit);
            tdeeQueryParamsDto.setAccountId(accountId);
            List<Tdee> tdees = this.tdeeService.findMany(tdeeQueryParamsDto);

            TrainingQueryParamsDto trainingQueryParamsDto = new TrainingQueryParamsDto();
            trainingQueryParamsDto.setOffset(0);
            trainingQueryParamsDto.setLimit(deletionLimit);
            trainingQueryParamsDto.setAccountId(accountId);
            List<Training> trainings = this.trainingService.findMany(trainingQueryParamsDto);

            DietQueryParamsDto dietQueryParamsDto = new DietQueryParamsDto();
            dietQueryParamsDto.setOffset(0);
            dietQueryParamsDto.setLimit(deletionLimit);
            dietQueryParamsDto.setAccountId(accountId);
            List<Diet> diets = this.dietService.findMany(dietQueryParamsDto);

            if (tdees.isEmpty() && trainings.isEmpty() && diets.isEmpty()) {
                Optional<Session> sessionOptional = this.sessionService.findByAccountId(accountId);
                
                if (sessionOptional.isPresent()) {
                    this.sessionService.deleteOne(sessionOptional.get().getId());
                }
                
                this.accountService.deleteOne(accountId);
                
                AccountDeletionCron.log.info(String.format(
                    "AccountDeletionCron finished with success: account with accountId \"%s\" and username \"%s\" permanently deleted",
                    accountId,
                    username
                ));
                return;
            }
 
            if (!tdees.isEmpty()) {
                List<String> tdeeIds = tdees.stream().map(e -> e.getId()).toList();
                this.tdeeService.deleteMany(new HashSet<String>(tdeeIds));
            }
            
            if (!trainings.isEmpty()) {
                List<String> trainingIds = trainings.stream().map(e -> e.getId()).toList();
                this.trainingService.deleteMany(new HashSet<String>(trainingIds));
            }
            
            if (!diets.isEmpty()) {
                List<String> dietIds = diets.stream().map(e -> e.getId()).toList();
                this.dietService.deleteMany(new HashSet<String>(dietIds));
            }

            AccountDeletionCron.log.info(String.format(
                "AccountDeletionCron finished with success: %d TDEEs, %d trainings and %d diets permanently deleted",
                tdees.size(),
                trainings.size(),
                diets.size()
            ));
        }
        catch (Exception exception) {
            AccountDeletionCron.log.info("AccountDeletionCron finished with error: " + exception.getMessage());
        }
    }
}
