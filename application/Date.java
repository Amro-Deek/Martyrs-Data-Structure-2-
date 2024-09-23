package application;

public class Date {
	private String date;

	public Date(String date) {
		super();
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		if (this.date==null) {
			date="null";
		}
		return "Date [date=" + date + "]";
	}

}
