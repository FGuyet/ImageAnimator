# Image Animator

## Overview
This is a small app developped in a few hours that contains 3 screens:

### Search Screen
- The user search for a topic to find images about.

### Image Selection
- A few results are loaded and displayed
    - If less than two images are found, then we redirect the user on the search screen, with an error message.
- The user selects several images and press the "Create animation" button
    - The "Create animation" button is enabled only if at least 2 images are currently selected.

### Animation Screen 
- A simple animation is displayed based on the selected images

## Set up
This project uses the [Pixabay API](https://pixabay.com/api/docs/). Therefore, before running this project, you will need to
Declare the `PIXABAY_KEY` property in your local Gradle properties.
