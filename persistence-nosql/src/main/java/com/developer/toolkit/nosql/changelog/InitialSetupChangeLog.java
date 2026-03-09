package com.developer.toolkit.nosql.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "init-schema", order = "001", author = "developer")
public class InitialSetupChangeLog {

    @Execution
    public void execution() {
        // Initial setup logic
    }

    @RollbackExecution
    public void rollback() {
        // Rollback logic
    }
}
