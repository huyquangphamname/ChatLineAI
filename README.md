# **ChatLineAI**

### **Overview**

**ChatLineAI** is an innovative project that integrates a Line bot with advanced **Natural Language Processing (NLP)** and the **ChatGPT API**, aiming to revolutionize the digital advertising landscape. The system analyzes user conversations in real-time, extracting relevant keywords to generate personalized, dynamic advertisements. This marks a significant shift from traditional static advertising methods, offering a more tailored and interactive experience.

---

### **System Architecture**

ChatLineAI is built on a robust architecture comprising three key components:

- **Line Bot**: Acts as the user interface, handling inputs and displaying the dynamically generated advertisements.
- **NLP Component**: Processes user inputs using tokenization, part-of-speech tagging, and named entity recognition to extract meaningful keywords.
- **ChatGPT API**: Leverages the extracted keywords to create visually appealing, personalized advertisements through the ChatGPT language model.

---

### **NLP Setup**

- **Data Storage**: Utilizes **Java HashMaps** for efficient storage and retrieval of user conversations, facilitating quick keyword extraction.
- **Keyword Extraction**: NLP component uses Apache NLP for extracting relevant keywords from user inputs, enabling personalized ad generation.

---

### **Prompt Engineering and ChatGPT Integration**

- **Keyword Utilization**: Extracted keywords are fed into ChatGPT API using carefully designed prompts to generate contextually relevant advertisements.
- **Ad Generation**: The ChatGPT API generates visually appealing ads that align with the user's conversation context.

---

### **Dynamic Ad Creation**

- **Personalization**: ChatLineAI generates unique ads based on each user's interactions, ensuring personalized and engaging content.
- **Real-Time Customization**: Ads are dynamically created in real-time using advanced algorithms and tools for instant delivery to users.

---

### **Technologies Used**

- **Java (Spring Boot Framework)**: Provides the scalable backbone of the system.
- **Apache NLP**: For sophisticated text processing and keyword extraction.
- **ChatGPT API**: To generate dynamic, personalized advertisements.
- **Feign**: Ensures efficient communication with the ChatGPT API for real-time ad generation.

---

### **Strengths of ChatLineAI**

- **Personalized Advertising**: Tailored to individual user preferences, boosting engagement.
- **Dynamic Content**: Real-time ad generation based on live user interactions.
- **Seamless Integration**: Efficient combination of Line Bot, NLP, and ChatGPT for automated advertising.
- **Scalable**: Built with Spring Boot, enabling high scalability and performance.

---

### **Areas for Improvement**

- **Handling Complex Keywords**: Processing conversations with high keyword density can reduce ad coherence. Future refinement in NLP is required to prioritize contextually relevant keywords.
- **User Feedback**: Initial testing was developer-led. Gathering real user feedback will provide valuable insights for further system improvement.
- **Creative Enhancement**: While ads are personalized, integrating more advanced AI models for image generation can enhance the visual appeal.

---

### **Conclusion**

**ChatLineAI** represents a promising future in AI-driven personalized advertising. By harnessing the power of NLP and the ChatGPT API, it opens up new possibilities for creating engaging, contextually relevant advertisements. Future improvements can further enhance its capabilities, potentially transforming the digital advertising industry.

---

### **How to Run the Project**

1. Clone the repository from GitHub:
   ```bash
   git clone https://github.com/huyquangphamname/ChatLineAI.git
2. Set up the required dependencies:

- Java (Spring Boot)
- Apache NLP
- Feign for API integration
3. Run the application using your favorite Java IDE or terminal:
     ```bash
     ./mvnw spring-boot:run
4. Ensure the Line Bot is set up and connected to your Line developer account for user interaction.
5. Test the system by initiating a conversation through the Line Bot interface and observe the dynamically generated advertisements based on user inputs.

---

### **Future Enhancements**
- Further improve the NLP component to handle complex conversations with better keyword prioritization.
- Integrate advanced AI models for improved ad creativity and personalization.
- Collect user feedback for ongoing refinements and system improvements.


---
**Project Link:** [ChatLineAI GitHub Repository](https://github.com/huyquangphamname/ChatLineAI.git)
