package id.co.skoline.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopicItemsResponse {
    @SerializedName("topic")
    @Expose
    private Topic topic;
    public Topic getTopic() {
        return topic;
    }
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public class Adventure {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("video_link")
        @Expose
        private Object videoLink;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getVideoLink() {
            return videoLink;
        }

        public void setVideoLink(Object videoLink) {
            this.videoLink = videoLink;
        }
    }
    public class Topic {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("adventure")
        @Expose
        private Adventure adventure;
        @SerializedName("game")
        @Expose
        private Object game;
        @SerializedName("challenge")
        @Expose
        private Object challenge;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Adventure getAdventure() {
            return adventure;
        }

        public void setAdventure(Adventure adventure) {
            this.adventure = adventure;
        }

        public Object getGame() {
            return game;
        }

        public void setGame(Object game) {
            this.game = game;
        }

        public Object getChallenge() {
            return challenge;
        }

        public void setChallenge(Object challenge) {
            this.challenge = challenge;
        }

    }
}
