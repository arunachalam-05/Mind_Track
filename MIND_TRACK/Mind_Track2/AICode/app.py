from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf

app = Flask(__name__)

# Load TFLite model
interpreter = tf.lite.Interpreter(model_path="mindtrack_model.tflite")
interpreter.allocate_tensors()
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

# 50 AI suggestions
suggestions = [ 
    "Take a 5-minute deep breathing session to lower your cortisol levels and improve oxygen flow to your brain. This will help reduce mental clutter and sharpen your focus before continuing with your task.",
    "Your cognitive load is currently high, which may lead to mental exhaustion if prolonged. Break down your study material into manageable chunks and take a 2–3 minute micro-break after every major concept.",
    "Fatigue has set in, which can impair memory and focus. Engage in light movement, such as stretching or a brisk walk, to re-energize your body and reset your mental alertness.",
    "Your focus levels are dropping, which can reduce learning efficiency. Eliminate environmental distractions, put your phone on silent, and set a small, achievable goal for the next 10 minutes to re-engage your concentration.",
    "Memory retention seems low, which might affect long-term understanding. Use mind maps, story associations, or visualization techniques to anchor complex ideas in your brain more effectively.",
    "Elevated stress levels detected. Try a 3-minute box breathing exercise (inhale for 4s, hold for 4s, exhale for 4s, hold for 4s) to calm your nervous system and improve emotional balance.",
    "Consider using the Pomodoro technique—study with full intensity for 25 minutes, followed by a 5-minute rest. This method boosts productivity while giving your brain structured rest periods.",
    "Strengthen memory by teaching someone else what you've learned, or explain the topic aloud. This activates recall and deeper neural pathways, improving retention and clarity.",
    "You haven’t studied for long yet, and pacing is key. Avoid rushing and set a steady rhythm to prevent mental burnout later. Build momentum gradually for longer retention.",
    "To reinforce memory, close your study materials and try recalling key points using the Active Recall technique. This strengthens the information stored in long-term memory.",
    "Your current correctness rate is moderate. Integrate spaced repetition apps like Anki into your study routine to revisit difficult concepts frequently and embed them more securely.",
    "To reduce stress while staying engaged, listen to instrumental music or calming ambient sounds in the background. This can create a relaxing yet focused environment.",
    "Slightly diminished focus detected. Pause to review your study goals, prioritize urgent topics, and restructure your tasks into clear, time-bound targets for better mental alignment.",
    "Both stress and cognitive load are rising. Take a 5-minute pause, drink some water, and give your brain the reset it needs before tackling the next subject.",
    "Physical stagnation may be affecting attention. Do some desk stretches or try standing up while reviewing flashcards to stimulate blood flow and brain activity.",
    "Fatigue is negatively impacting your accuracy. Try a 10-minute power nap or practice simple eye relaxation exercises to reduce strain and increase alertness.",
    "You’re maintaining moderate focus, but stress is creeping in. Take 5 minutes to practice guided mindfulness meditation to center your thoughts and stay in control.",
    "Your memory is sharp and stress is low—this is the ideal time to approach more abstract or complex subjects, as your brain is primed for high-level thinking.",
    "A performance dip is visible. Return to simpler concepts and reinforce your foundation before advancing. Repetition at this stage is more helpful than rushing forward.",
    "Both fatigue and study time are high, signaling cognitive depletion. Aim to study during peak energy periods, such as late morning or early evening, for better outcomes.",
    "Strong focus and memory have been detected. Now is the best moment to test your knowledge through timed quizzes or application-based questions for deeper learning.",
    "You're doing well! Acknowledge your progress with a short break, listen to some music, or have a nutritious snack to reward your brain and sustain motivation.",
    "Your brain is showing signs of deep learning, which is the perfect moment to consolidate knowledge by rewriting notes in your own words or diagramming connections.",
    "You’re maintaining calm under pressure, which is a sign of growing resilience. Stick to your current strategies and continue progressing with confidence.",
    "Stress levels are low and accuracy is high. This balance presents a good opportunity to challenge yourself with more advanced practice questions or case-based scenarios.",
    "Your mental rhythm is highly productive. Maintain this momentum with consistent habits and periodic reflection on what strategies work best for you.",
    "You’re achieving steady performance. Use this time to identify weaker areas and dedicate 20 minutes to reinforcing them using focused exercises or tutoring videos.",
    "Fatigue is well-managed, allowing sustained productivity. Keep a water bottle nearby and sip throughout the session to maintain hydration and cognitive clarity.",
    "Cognitive load is currently low, which presents a great opportunity to increase difficulty slightly—take on a complex task or explore a new subject now.",
    "Your correctness score is at 40%, suggesting a gap in speed or comprehension. Try solving similar questions under a timer to improve both accuracy and time efficiency.",
    "Stress appears to be in check. Consider inviting a peer to join you in a study session. Group discussions often enhance retention and make learning more engaging.",
    "Deepen memory formation by journaling your key takeaways at the end of each session. This reflection reinforces what you’ve learned and highlights improvement areas.",
    "Your time usage is optimal, which shows good pacing. Now, transition to evaluating your comprehension with self-assessments or mock tests to prepare for application.",
    "You’ve made great progress so far! Take a well-deserved 10-minute break, stretch, hydrate, and return with renewed clarity and determination.",
    "Lack of quality sleep might be affecting memory. Aim to get at least 7–8 hours of uninterrupted rest tonight to give your brain time to consolidate today's learning.",
    "Cognitive load and memory are balanced, making it an ideal moment for problem-solving or application-based tasks that require layered thinking.",
    "Consistency builds excellence. Continue with your current routines, and focus on maintaining accuracy by reviewing errors and learning from them.",
    "If you're feeling mentally blocked, try switching topics or alternating between theory and practical content to relieve cognitive saturation and spark creativity.",
    "Recharge your mind creatively with light puzzles, brain games, or a quick sketch session. These activities promote lateral thinking and improve problem-solving skills.",
    "Focus and accuracy are both strong, indicating your readiness for more rigorous academic challenges. Dive into those hard problems you’ve been putting off!",
    "To deepen your understanding, use a whiteboard or teach the topic out loud as if to a class. This forces your brain to organize knowledge logically and clearly.",
    "Prevent distraction by setting your phone to Do Not Disturb mode and closing unnecessary tabs. This will help you stay in a state of flow and boost deep focus.",
    "Very low fatigue detected, suggesting your brain is fresh and ready. Use this window to absorb new information or tackle subjects requiring long-term memory.",
    "If accuracy drops under time pressure, slow down your pace slightly. Precision is more valuable than speed, especially in complex subjects.",
    "Your mental clarity is commendable. Reinforce your study by making one-page summary sheets or condensed cheat notes for quick future revision.",
    "Mix physical movement with review—try listening to pre-recorded notes while walking to integrate body and brain engagement for improved recall.",
    "Your accuracy is impressive today. Take advantage by teaching the concept to a friend, peer, or even explaining it to yourself out loud to reinforce it.",
    "You're almost peaking in performance! Make the most of it by journaling your learnings and planning tomorrow’s focus areas while your memory is fresh.",
    "Model suggests optimal brain activity. It's an ideal moment to plan your next milestone or study target. Consider preparing for a quiz or practical test.",
    "You’re performing excellently—maintaining sharp focus, low stress, and high correctness. Keep building on this productive streak with short breaks and reflection."
]

@app.route('/predict', methods=['POST'])
def suggest():

    
    try:
        data = request.json
        eeg_input = np.array([[
            data['Cognitive_Load'],
            data['Stress'],
            data['Focus'],
            data['Memory'],
            data['Fatigue'],
            data['Time_Seconds'],
            data['Correctness']
        ]], dtype=np.float32)

        interpreter.set_tensor(input_details[0]['index'], eeg_input)
        interpreter.invoke()
        output_data = interpreter.get_tensor(output_details[0]['index'])
        predicted_index = int(np.argmax(output_data))

        return jsonify({
            'suggestion': suggestions[predicted_index]
        })

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
