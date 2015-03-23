package com.hp.avmon.classMgr.web;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.deploy.service.DeployService;
import com.hp.avmon.equipmentCenter.service.EquipmentCenterService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/classMgr/*")
public class ClassMgrController {

	private static final Logger logger = LoggerFactory
			.getLogger(ClassMgrController.class);

	@Autowired
	private DeployService deployService;
	
	@Autowired
	private EquipmentCenterService equipmentCenterService;
	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/classMgr/index";

	}

	}
