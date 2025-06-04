from flask import Flask, request, jsonify
import pandas as pd
from sklearn.neighbors import KNeighborsClassifier

# Initialize Flask app
app = Flask(__name__)

# -----------------------------------------------
# 1. Prepare the Dataset and Train the Model

data = {
    "Cognitive_Load": [
        0.70, 0.62, 0.80, 0.48, 0.65,
        0.40, 0.55, 0.73, 0.36, 0.50,
        0.45, 0.55, 0.29, 0.42, 0.60,
        0.47, 0.68, 0.38, 0.53, 0.30,
        0.30, 0.25, 0.35, 0.28, 0.32
    ],
    "Stress": [
        0.75, 0.70, 0.85, 0.50, 0.70,
        0.45, 0.60, 0.78, 0.40, 0.55,
        0.50, 0.58, 0.30, 0.45, 0.65,
        0.52, 0.72, 0.42, 0.58, 0.25,
        0.28, 0.22, 0.33, 0.30, 0.26
    ],
    "Focus": [
        0.30, 0.45, 0.25, 0.75, 0.35,
        0.78, 0.60, 0.28, 0.85, 0.65,
        0.78, 0.60, 0.90, 0.77, 0.58,
        0.68, 0.40, 0.82, 0.62, 0.90,
        0.85, 0.78, 0.88, 0.82, 0.80
    ],
    "Memory": [
        0.40, 0.55, 0.35, 0.65, 0.50,
        0.70, 0.65, 0.38, 0.80, 0.67,
        0.75, 0.62, 0.85, 0.73, 0.60,
        0.65, 0.45, 0.78, 0.64, 0.85,
        0.82, 0.80, 0.75, 0.79, 0.81
    ],
    "Fatigue": [
        0.72, 0.68, 0.80, 0.45, 0.70,
        0.42, 0.50, 0.75, 0.38, 0.50,
        0.43, 0.54, 0.28, 0.40, 0.55,
        0.48, 0.71, 0.37, 0.53, 0.25,
        0.30, 0.25, 0.32, 0.27, 0.29
    ],
    "Time_Seconds": [
        40, 38, 50, 20, 36,
        22, 25, 42, 16, 24,
        17, 23, 12, 19, 28,
        20, 37, 16, 22, 14,
        15, 18, 17, 16, 19
    ],
    "Correctness": [
        0, 0, 0, 1, 0,
        1, 1, 0, 1, 1,
        1, 1, 1, 1, 1,
        1, 0, 1, 1, 1,
        1, 1, 1, 1, 1
    ],
    "Suggestion_Index": list(range(25))
}


suggestions = [
     "Practice 5 minutes of deep breathing exercises to lower stress hormones and enhance oxygen flow to your brain before continuing your study session.",
    "Take a 10-minute break away from the screen to refresh your cognitive resources, stretch your muscles, and reset your mental focus.",
    "Engage in mindfulness meditation for at least 10 minutes to calm your mind, regulate emotions, and improve your information processing capacity.",
    "Apply the Pomodoro technique: Study intensively for 25 minutes followed by a 5-minute complete break to maintain mental sharpness without fatigue.",
    "Perform light physical exercises such as walking or stretching to improve blood circulation and boost overall brain function and energy levels.",
    "Revise your studied material using mind maps and flashcards, which are proven to enhance memory retention through visual learning techniques.",
    "Listen to soft instrumental or low-frequency background music, scientifically shown to increase attention span and reduce study distractions.",
    "Remove nearby distractions: Turn off smartphone notifications, close unnecessary tabs, and create a distraction-free study zone to maximize focus.",
    "Practice focused reading sessions by setting a goal to read a specific number of pages or topics within a time frame to train your sustained attention.",
    "Ensure you are getting 7–8 hours of quality sleep daily to consolidate your memory, restore cognitive functions, and enhance learning outcomes.",
    "Stay properly hydrated by drinking enough water throughout your study periods, as dehydration can severely impair short-term memory and focus.",
    "Create and follow a consistent study schedule that includes planned breaks, healthy meals, and dedicated revision times to build strong study habits.",
    "Engage in brain-training activities like puzzles, Sudoku, or memory games for 10 minutes daily to stimulate neuroplasticity and enhance mental agility.",
    "Visualize complex information by imagining scenarios, drawing diagrams, or story-lining the content to build stronger and long-lasting mental connections.",
    "Focus on one task at a time instead of multitasking; single-tasking is proven to dramatically improve efficiency, reduce cognitive load, and enhance memory retention.",
    "After each study session, self-test your understanding by attempting short quizzes or explaining concepts aloud to yourself to strengthen learning retention.",
    "Perform progressive muscle relaxation: Contract and relax different muscle groups systematically to release tension, enhance focus, and calm the mind.",
    "Organize your physical and digital study environments by keeping only necessary materials around; a clean space greatly enhances mental clarity and reduces stress.",
    "If mental fatigue is detected, practice gentle stretching or yoga exercises for a few minutes to stimulate physical alertness and rejuvenate brain activity.",
    "Use the Active Recall Technique: Close your book and attempt to retrieve information from memory without looking at your notes to boost long-term retention.",
    "Great job! Your focus and mental alertness are excellent. Keep up this level of engagement to achieve outstanding academic success.",
    "Your cognitive state is showing optimal performance! Maintain this balance with consistent study habits and regular mental relaxation.",
    "Well done! Your brainwaves reflect sharp attention and strong memory retention. You are on the right path to mastering your subjects.",
    "Fantastic performance! Your low stress and high focus indicate strong resilience. Keep using the techniques that help you stay calm and focused.",
    "Excellent cognitive control detected! Stay consistent with your routines, and you will continue excelling both mentally and academically."
]


# Train the model
df = pd.DataFrame(data)
X = df.drop('Suggestion_Index', axis=1)
y = df['Suggestion_Index']

knn = KNeighborsClassifier(n_neighbors=1)
knn.fit(X, y)

# -----------------------------------------------
# 2. Define Suggestion Prediction Function

def predict_suggestion(input_data):
    feature_order = ["Cognitive_Load", "Stress", "Focus", "Memory", "Fatigue", "Time_Seconds", "Correctness"]
    
    input_data_ordered = {feature: input_data.get(feature) for feature in feature_order}

    input_df = pd.DataFrame([input_data_ordered])
    
    prediction = knn.predict(input_df)[0]
    return suggestions[prediction]

# -----------------------------------------------
# 3. Routes

@app.route('/')
def home():
    return "🧠 MindTrack AI is Running!"

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json

    print("Received data:", data)

    required_keys = ["Cognitive_Load", "Stress", "Focus", "Memory", "Fatigue", "Time_Seconds", "Correctness"]

    if not all(key in data for key in required_keys):
        return jsonify({"error": "Missing inputs"}), 400

    suggestion = predict_suggestion(data)

    return jsonify({
        "suggestion": suggestion
    })

# -----------------------------------------------
# 4. Run the app
if __name__ == '__main__':
    app.run(debug=True)
