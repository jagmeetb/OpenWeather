package jagmeet.weather;

/**
 * Created by Jagmeet on Nov 30 2016.
 */

public class DetailItem {
	private String cityID;
	private String cityName;
	private String weatherID;
	private String temp;
	private String min;
	private String max;
	private String humidity;
	private String main;
	private String description;
	private String sunrise;
	private String sunset;

	public DetailItem(String cityID, String cityName, String temp, String min, String max, String humidity, String weatherID, String main, String description, String sunrise, String sunset){
		this.cityID = cityID;
		this.cityName = cityName;
		this.weatherID = weatherID;
		this.temp = temp;
		this.min = min;
		this.max = max;
		this.humidity = humidity;
		this.main = main;
		this.description = description;
		this.sunrise = sunrise;
		this.sunset = sunset;
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
	public String getMin() { return min; }
	public String getMax() { return max; }
	public String getHumidity() { return humidity; }
	public String getSunrise() { return sunrise; }
	public String getSunset() { return sunset; }

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
	public void setMin(String min) { this.min = min; }
	public void setMax(String max) { this.max = max; }
	public void setHumidity(String humidity) { this.humidity = humidity; }
	public void setSunrise(String sunrise) { this.sunrise = sunrise; }
	public void setSunset(String sunset) { this.sunset = sunset; }

	@Override
	public String toString(){
		return String.valueOf(cityName);
	}
}

