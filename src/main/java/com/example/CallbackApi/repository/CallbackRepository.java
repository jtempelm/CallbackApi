package com.example.CallbackApi.repository;

import com.example.CallbackApi.model.Callback;
import org.springframework.data.repository.CrudRepository;

public interface CallbackRepository extends CrudRepository<Callback, Long> {

    Callback findById(final long id);

    Callback findByCallbackId(final String callbackId);

}
