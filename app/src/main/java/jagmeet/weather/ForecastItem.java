package jagmeet.weather;

/**
 * Created by Jagmeet on Nov 30 2016.
 */

public class ForecastItem {
	private String id;
	private String main;
	private String description;
	public String temp;
	public String date;

	public ForecastItem(String id, String main, String description, String temp){
		this.id = id;
		this.main = main;
		this.description = description;
		this.temp = temp;
	}

	//GETTERS
	public String getId(){
		return id;
	}
	public String getMain() { return main; }
	public String getDescription() { return description; }
	public String getTemp() { return temp; }


	//SETTERS
	public void setId(String id) { this.id = id; }
	public void setMain(String main) { this.main = main; }
	public void setDescription(String description) { this.description = description; }
	public void setTemp(String temp) { this.temp = temp; }
}
