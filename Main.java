
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
		} else {
			System.out.println("Skeletonized finished. . .");
		}
	}

	public static void Skeletonize(File input, File export) {
		File[] takes = input.listFiles();

		for (File takesI : takes) {

			if (takesI.length() > 0) {
				try {

					File exportedFile = new File(export.toPath() + "\\" + takesI.getName());

					System.out.println(takesI.toPath() + "\n" + export.toPath());
					System.out.println("Copying file: " + takesI.getName());
					Files.copy(takesI.toPath(), exportedFile.toPath());
					System.out.println("Copied file: " + takesI.getName());

					if (skeletonize) {
						String fullPath = takesI.getCanonicalPath();
						takesI.delete();

						File skeleton = new File(fullPath);

						skeleton.createNewFile();
						System.out.println("Skeleton created: " + skeleton);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(
						"File \"" + takesI.getName() + "\" is under 1 kb, is a suspected skeleton, skipping. . .");
			}
		}
	}

}
