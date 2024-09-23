package application;

public class DateStat {
	private String date;
	private String maxDis;
	private String maxLoc;
	private int totalMartyrs;
	private double avgAges;

	public DateStat(String date, String maxDis, String maxLoc, int totalMartyrs, double avgAges) {
		super();
		this.date = date;
		this.maxDis = maxDis;
		this.maxLoc = maxLoc;
		this.totalMartyrs = totalMartyrs;
		this.avgAges = avgAges;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMaxDis() {
		return maxDis;
	}

	public void setMaxDis(String maxDis) {
		this.maxDis = maxDis;
	}

	public String getMaxLoc() {
		return maxLoc;
	}

	public void setMaxLoc(String maxLoc) {
		this.maxLoc = maxLoc;
	}

	public int getTotalMartyrs() {
		return totalMartyrs;
	}

	public void setTotalMartyrs(int totalMartyrs) {
		this.totalMartyrs = totalMartyrs;
	}

	public double getAvgAges() {
		return avgAges;
	}

	public void setAvgAges(double avgAges) {
		this.avgAges = avgAges;
	}

	@Override
	public String toString() {
		return "DateStat [date=" + date + ", maxDis=" + maxDis + ", maxLoc=" + maxLoc + ", totalMartyrs=" + totalMartyrs
				+ ", avgAges=" + avgAges + "]";
	}

}
