package net.siinergy.dsc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

@Service
public class UnZipFile {
	List<String> fileList;
	private static final String INPUT_ZIP_FILE = "osc_cve.zip";
	private static final String OUTPUT_FOLDER = "";

	
	/**
	 * Unzip it
	 * 
	 * @param zipFile input zip file
	 * @param output  zip file output folder
	 */
	public void unZipFile(String zipFile, String outputFolder) {

		byte[] buffer = new byte[1024];

		try {

			// create output directory is not exists
			//File folder = new File(outputFolder);
			//if (!folder.exists()) {
			//	folder.mkdir();
			//}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(  fileName);

				System.out.println("file unzip : " + newFile);

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				//new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String downloadFile(final String sourceUrl,String toFile) {
		String fromFile = sourceUrl;
	
		try {

			URL website = new URL(fromFile);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(toFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return toFile;
	}

}
