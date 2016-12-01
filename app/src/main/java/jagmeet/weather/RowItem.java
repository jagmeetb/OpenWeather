package jagmeet.weather;

/**
 * Created by Jagmeet on Nov 17 2016.
 */

public class RowItem {
	private String cityID;
	private String cityName;
	private String weatherID;
	private String temp;
	private String main;
	private String description;

	public RowItem(String cityID, String cityName, String temp, String weatherID, String main, String description){
		this.cityID = cityID;
		this.cityName = cityName;
		this.temp = temp;
		this.weatherID = weatherID;
		this.main = main;
		this.description = description;
	}

	//GETTERS
	public String getCityID(){
		return cityID;
	}
	public String getCityName() {return cityName; }
	public String getTemp(){
		return temp;
	}
	public String getWeatherID() { return weatherID; }
	public String getMain(){
		return main;
	}
	public String getDescription(){
		return description;
	}

	//SETTERS
	public void setCityID(String cityID){
		this.cityID = cityID;
	}
	public void setCityName(String cityName) { this.cityName = cityName; }
	public void setTemp(String temp){
		this.temp = temp;
	}
	public void setWeatherID(String weatherID) {this.weatherID = weatherID; }
	public void setMain(String main){
		this.main = main;
	}
	public void setDescription(String description){
		this.description = description;
	}

	@Override
	public String toString(){
		return String.valueOf(cityName);
	}
}
