package com.example.foodplanner.OnBoarding.Models.Ingredient;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

	@SerializedName("strDescription")
	private String strDescription;

	@SerializedName("strIngredient")
	private String strIngredient;

	@SerializedName("strType")
	private String strType;

	@SerializedName("idIngredient")
	private String idIngredient;


	public final static String urlBase="https://www.themealdb.com/images/ingredients/";
	public final static String urlTail="-Small.png";

	public String getStIngredientThumb() {
		return urlBase+getStrIngredient()+urlTail;}

	public String getStrDescription(){
		return strDescription;
	}

	public String getStrIngredient(){
		return strIngredient;
	}

	public String getStrType(){
		return strType;
	}

	public String getIdIngredient(){
		return idIngredient;
	}
}