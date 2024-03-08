package com.example.demo.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {
	public byte[] generateReport(String pathToReport, Map<String, Object> params,
			JRBeanCollectionDataSource jrb) throws JRException {
		
		JasperReport jasperReport = JasperCompileManager
				.compileReport(getClass().getResourceAsStream(pathToReport));
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrb);

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
}
