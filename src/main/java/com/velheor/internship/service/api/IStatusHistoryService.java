package com.velheor.internship.service.api;

import com.velheor.internship.models.StatusHistory;
import java.util.UUID;

public interface IStatusHistoryService extends Crud<StatusHistory> {

    IStatusHistoryService findById(UUID id);
}
