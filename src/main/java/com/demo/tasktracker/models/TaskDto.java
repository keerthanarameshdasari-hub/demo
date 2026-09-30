package com.demo.tasktracker.models;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class TaskDto{
        @NotEmpty(message = "The title is required")
        private String title;

        //@NotEmpty(message = "The description is required")
        @Size(min = 10,max = 2000)
        private String description;

       // @NotEmpty(message = "The due date is required")
        private String dueDate;

        @NotEmpty(message = "The status is required")
        private String status;

        @NotEmpty(message = "The priority is required")
        private String priority;


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

        public String getDueDate() {
                return dueDate;
        }

        public void setDueDate(String dueDate) {
                this.dueDate = dueDate;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getPriority() {
                return priority;
        }

        public void setPriority(String priority) {
                this.priority = priority;
        }


}
