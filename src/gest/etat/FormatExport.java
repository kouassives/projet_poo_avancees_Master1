package gest.etat;

import java.io.File;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public enum FormatExport {
	PDF("/images/export/Adobe-Acrobat-48.png", "PDF (*.pdf)") {
		@Override
		void export(JasperPrint print, File fichier) throws JRException {
			JasperExportManager.exportReportToPdfFile(print,
					fichier.getAbsolutePath());
		}
	},
	HTML("/images/export/Microsoft-Office-2013-02-48.png",
			"Document MS Office (*.docx)") {
		@Override
		void export(JasperPrint print, File fichier) throws JRException {
			JasperExportManager.exportReportToHtmlFile(print,
					fichier.getAbsolutePath());
		}
	},
	DOCX("/images/export/Firefox-48.png", "Page web (*.html)") {
		@Override
		JRDocxExporter creerExporter() {
			return new JRDocxExporter();
		}
	},
	ODT("/images/export/odt.png", "Document Open/Libre Office (*.odt)") {
		@Override
		JROdtExporter creerExporter() {
			return new JROdtExporter();
		}
	},
	ODS("/images/export/ods.png", "Tableur Open/Libre Office (*.ods)") {
		@Override
		JROdsExporter creerExporter() {
			return new JROdsExporter();
		}
	},
	;

	private final ImageIcon icone;
	private final String fichier;

	@SuppressWarnings("rawtypes")
	JRAbstractExporter creerExporter() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void export(JasperPrint print, File fichier) throws JRException {
		JRAbstractExporter exporter = creerExporter();
		exporter.setExporterInput(new SimpleExporterInput(print));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
				fichier));
		exporter.exportReport();
	}

	FormatExport(String icone, String fichier) {
		this.icone = new ImageIcon(FormatExport.class.getResource(icone));
		this.fichier = fichier;
	}

	public ImageIcon getIcone() {
		return icone;
	}

	public String getDescription() {
		return fichier;
	}
}