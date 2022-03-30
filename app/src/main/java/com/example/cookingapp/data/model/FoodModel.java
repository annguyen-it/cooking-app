package com.example.cookingapp.data.model;

import java.util.Date;

public class FoodModel {
        private int id;
        private int idOwner;
        private String userName;
        private String name;
        private int idImage;
        private String description;
        private boolean isVegetarian;
        private int difficultLevel;
        private int timeToCook;
        private String country;
        private Date timePost;
        private String ingredient;
        private String tips;
        private float rate;
        private int voteCount;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdOwner() {
            return idOwner;
        }

        public void setIdOwner(int idOwner) {
            this.idOwner = idOwner;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIdImage() {
            return idImage;
        }

        public void setIdImage(int idImage) {
            this.idImage = idImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isVegetarian() {
            return isVegetarian;
        }

        public void setVegetarian(boolean vegetarian) {
            isVegetarian = vegetarian;
        }

        public int getDifficultLevel() {
            return difficultLevel;
        }

        public void setDifficultLevel(int difficultLevel) {
            this.difficultLevel = difficultLevel;
        }

        public int getTimeToCook() {
            return timeToCook;
        }

        public void setTimeToCook(int timeToCook) {
            this.timeToCook = timeToCook;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Date getTimePost() {
            return timePost;
        }

        public void setTimePost(Date timePost) {
            this.timePost = timePost;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

    }


