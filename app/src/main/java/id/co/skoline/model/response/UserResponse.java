package id.co.skoline.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {

        @SerializedName("child_name")
        @Expose
        private String childName;
        @SerializedName("unique_name")
        @Expose
        private String uniqueName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("birth_date")
        @Expose
        private String birthDate;
        @SerializedName("email")
        @Expose
        private Object email;
        @SerializedName("location")
        @Expose
        private Object location;
        @SerializedName("newsletter")
        @Expose
        private Object newsletter;
        @SerializedName("avatar_url")
        @Expose
        private Object avatarUrl;

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }

        public String getUniqueName() {
            return uniqueName;
        }

        public void setUniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getNewsletter() {
            return newsletter;
        }

        public void setNewsletter(Object newsletter) {
            this.newsletter = newsletter;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

    }
}
