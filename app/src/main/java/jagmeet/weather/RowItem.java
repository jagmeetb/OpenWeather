package jagmeet.weather;

/**
 * Created by Jagmeet on Nov 17 2016.
 */

public class RowItem {
	private String cityID;
	private String temp;
	private String main;
	private String description;

	public RowItem(String cityID, String temp, String main, String description){
		this.cityID = cityID;
		this.temp = temp;
		this.main = main;
		this.description = description;
	}

	//GETTERS
	public String getCityID(){
		return cityID;
	}
	public String getTemp(){
		return temp;
	}
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
	public void setTemp(String temp){
		this.temp = temp;
	}
	public void setMain(String main){
		this.main = main;
	}
	public void setDescription(String description){
		this.description = description;
	}

	@Override
	public String toString(){
		return String.valueOf(cityID);
	}
}
