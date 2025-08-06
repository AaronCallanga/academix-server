package com.academix.academix.document.request.controller;

import com.academix.academix.document.request.facade.api.AdminDocumentRequestFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/document-requests")
@RequiredArgsConstructor
public class AdminDocumentRequestController {
    private final AdminDocumentRequestFacade adminDocumentRequestFacade;


}
