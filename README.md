Personal Event Planner App
SIT305 — Task 4.1P | Android Development

Overview
The Personal Event Planner App is an Android application that allows users to organise their upcoming events, trips, and appointments in one place. The app features a clean, intuitive interface backed by a local Room database, ensuring all data persists even after the app is closed or the device is restarted.
This project was developed as part of Task 4.1P for the SIT305 Mobile Application Development unit.

Features
Core Functionality (CRUD)

Create — Add new events with a Title, Category, Location, and Date/Time
Read — View all upcoming events on a dashboard, automatically sorted by date
Update — Edit any existing event's details
Delete — Remove events you no longer need, with confirmation feedback

Data Persistence

All event data is stored locally using the Room Persistence Library
Data survives app closure and device restarts
Events are sorted by date using millisecond timestamps stored alongside each event

Modern Navigation

Built with the Jetpack Navigation Component
Bottom Navigation Bar for switching between the Event List and Add Event screens
Uses Fragments instead of multiple Activities for a smoother experience

Validation & Error Handling

Title field must not be empty before saving
Date and Time must be selected before saving
New events cannot be set to a date in the past
Snackbar notifications confirm successful actions and display validation errors

**App Screens
Event List (Dashboard)

Displays all saved events as cards sorted by date
Each card shows the event Title, Category, Location, and Date/Time
Each card has an Edit button and a Delete button
Accessible via the bottom navigation bar

Add / Edit Event

Form screen with fields for Title, Category (dropdown), Location, and Date/Time
Date and Time are selected using Android's native DatePickerDialog and TimePickerDialog
When editing, all fields are pre-filled with the existing event's data
Accessible via the bottom navigation bar or by tapping Edit on a card**
