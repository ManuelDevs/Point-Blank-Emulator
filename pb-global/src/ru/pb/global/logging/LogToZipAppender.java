/*
 * Java Server Emulator Project Blackout / PointBlank
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: DarkSkeleton, sjke, Felixx
 * Copyright (C) 2013 PBDev™
 */

package ru.pb.global.logging;

import ch.qos.logback.core.FileAppender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Зипер для лог файлов
 *
 * @author sjke
 */
public class LogToZipAppender extends FileAppender {

	private String backupDir = "logs/backup";
	private String backupDateFormat = "yyyy-MM-dd HH-mm-ss";

	@Override
	public void setFile(String fileName) {
		toZip(new File(fileName));
		super.setFile(fileName);
	}

	protected void toZip(File file) {
		if (FileUtils.isFileOlder(file, ManagementFactory.getRuntimeMXBean().getStartTime())) {
			File backupRoot = new File(getBackupDir());
			if (!backupRoot.exists() && !backupRoot.mkdirs()) {
				throw new IllegalStateException("Can't create backup dir for backup storage");
			}
			String date = new SimpleDateFormat(getBackupDateFormat()).format(new Date(file.lastModified()));
			File zipFile = new File(backupRoot, file.getName() + "." + date + ".zip");
			ZipOutputStream zos = null;
			FileInputStream fis = null;
			try {
				zos = new ZipOutputStream(new FileOutputStream(zipFile));
				ZipEntry entry = new ZipEntry(file.getName());
				entry.setMethod(ZipEntry.DEFLATED);
				entry.setCrc(FileUtils.checksumCRC32(file));
				zos.putNextEntry(entry);
				fis = FileUtils.openInputStream(file);
				byte[] buffer = new byte[1024];
				int readed;
				while ((readed = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, readed);
				}
			} catch (Exception e) {
				throw new IllegalStateException("Can't create zip file", e);
			} finally {
				try {
					zos.close();
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public String getBackupDir() {
		return backupDir;
	}

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	public String getBackupDateFormat() {
		return backupDateFormat;
	}

	public void setBackupDateFormat(String backupDateFormat) {
		this.backupDateFormat = backupDateFormat;
	}
}
