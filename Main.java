package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	public static Date date = new Date(System.currentTimeMillis());
	public static String timeC = formatter.format(date);

	public static boolean skeletonize = false;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Paste the directory you wish to skeleonize. . .");
		String intake = scan.nextLine();
		System.out.println("Paste the export directory. . .");
		String exportLoc = scan.nextLine();
		File take = new File(intake.replace("\"", ""));
		scan.close();
		if (!exportLoc.endsWith("\\")) {
			exportLoc=exportLoc+"\\";
		}
		File exportDir = new File(exportLoc.replace("\"", "") + timeC + "_"
				+ take.toString().replace(":", "-").replace("\\", "-") + "\\");
		skeletonize = true;
		if (!exportDir.exists()) {
			exportDir.mkdir();
		}
		System.out.println("Exporting to: " + exportDir);
		Skeletonize(take, exportDir);
		if (exportDir.listFiles().length==0) {
			exportDir.delete();
			System.out.println("No new files. . .\n Deleted the created empty directory. . .");
		}
	}

	public static void Skeletonize(File input, File export) {
		File[] takes = input.listFiles();

		for (File takesI : takes) {
			/*
			 * if(takesI.isDirectory()){ File nestedFolder = new
			 * File(export+"\\"+takesI.getName()+"\\");
			 * 
			 * try { nestedFolder.createNewFile(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } Skeletonize(takesI,
			 * nestedFolder);
			 * 
			 * }
			 */
			if (takesI.length() > 0) {
				try {

					File exportedFile = new File(export.toPath() + "\\" + takesI.getName());

					System.out.println(takesI.toPath() + "\n" + export.toPath());
					System.out.println("Copying file: " + takesI.getName());
					Files.copy(takesI.toPath(), exportedFile.toPath());
					System.out.println("Copied file: " + takesI.getName());

					if (skeletonize) {
						String name = takesI.getName();
						String fullPath = takesI.getCanonicalPath();
						takesI.delete();

						File skeleton = new File(fullPath);

						skeleton.createNewFile();
						System.out.println("Skeleton created: " + skeleton);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println(
						"File \"" + takesI.getName() + "\" is under 1 kb, is a suspected skeleton, skipping. . .");
			}
		}
	}

}

/*
 * 
 * String path = "C:\\Users\\lever\\Documents\\delete with replace"; File dir =
 * new File(path); File export = null; skeletonize = true; String[] dirPaths =
 * dir.list();
 * 
 * 
 * 
 * for (String dirIterd : dirPaths) { if (dir.isDirectory()) { File fileIter =
 * new File(dirIterd); // List<String> dirPathList = new
 * ArrayList<String>().addAll(dirPaths); // dirPathIterable =
 * 
 * if (fileIter.length()<1) {
 * 
 * Path copyFrom = fileIter.toPath(); System.out.println("Copying file: " +
 * copyFrom); try { File exportFolder= new
 * File("T:\\backup from mass delrep\\"+timeC+"\\"); export = new
 * File(exportFolder+"\\"+fileIter.getCanonicalFile().getName());
 * if(!exportFolder.exists()) { exportFolder.mkdir(); }
 * 
 * Files.copy(copyFrom, export.toPath());
 * 
 * if (skeletonize) {
 * 
 * String name = fileIter.getName(); // fileIter.delete(); //
 * fileIter.createNewFile(); fileIter.createNewFile();
 * 
 * } } catch (FileAlreadyExistsException e) { // TODO Auto-generated catch block
 * //e.printStackTrace();
 * System.out.println("File already exists: "+export.toPath()); } catch
 * (FileNotFoundException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
 * block e.printStackTrace(); } } else {
 * System.out.println("File is size 0, skipping. . .\n"+fileIter.getName()); }
 * 
 * } else if (dir.isFile()) { Path copyFrom = dir.getAbsoluteFile().toPath();
 * System.out.println("Copying single file: " + dir.getName()); try {
 * Files.copy(copyFrom, export.toPath()); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } else {
 * System.out.println(
 * "Sorry, the supplied path doesnt lead to a file or directory, or the permission to read it has not been granted. . ."
 * ); } }
 */
