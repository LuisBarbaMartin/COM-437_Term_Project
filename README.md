# COM-437-26SU1-TermProject
This is a github repo for my COM-437 term project.

## Project description
IDLE-MMO-Manager is an **incremental game** that mimics the daily actions of running a Guild, organizing events, and dispatching your guild members on quests to not only build value in your guild bank, but also gain noteriety, and train your guild members to become stronger. <br>

### What is an Incremental Game?:
An incremental game is one that steadily unlocks progress over time. They do not require active game play to increase this progress, instead, they offer an experience that allows a user to set up tasks that are completed over a defined, or indefinite period of time. <br>

They're not intended to be played like traditional games where you sit and focus for extended periods of time. Instead, a user can queue tasks, close/leave the game, and return hours/days later to reap the rewards of their offline progress. <br>

## Problem addressing
As I've grown older, I've lost the ability to play games as often as I would like, as well the enthusiasm required to "get good" at games by pouring hundreds of hours into a single game. What I haven't lost is the love for gaming, and their settings. <br>

By creating an incremental game, I intend to create a casual experience that mimics the experience of playing an **MMORPG**. <br>

## Platform
This app will begin to be developed with Android SDK, for the Android Mobile platform. Since the course is based on Mobile App Development for Android, that will be the primary platfom for development, testing, and deployment. <br>



## Front/Back end support
I am considering using Java/Kotlin to build the project, but at my current stage in learning, i'm must comfortable with HTML/CSS/JS (vanilla + React), and Python + Flask for constructing websites. If I can find a way to use these languages for the assignment, that would be my preferred method of building the app. <br>

## Functionality

## Design (wireframes)

### Screen 1: Start Screen

```text
+------------------------------------------------+
|                                                |
|                                                |
|                                                |
|                                                |
|                  +----------+                  |
|                  |  START   |                  |
|                  +----------+                  |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
```
The landing screen users will see when they first start the app. There will be artwork displayed here establishing the theme/lore of the app. <br>

### Screen 2: Dashboard
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| active_quest_focus_panel                       |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| roster                                         |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
```
This will be the first screen users will see after passing the Start Screen. <br>
This screen will display the Guild name the user chose, as well as their total fame or fame level (to be determined), and their total currency. <br>

A row of menu navigation tables are visible at the bottom of the screen, allowing the user to navigate the various panels required to use the app. <br>


### Screen 3: Guild Bank Tab
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| guild_bank                                     |
|                                                |
| +--------------------------------------------+ |
| |                                            | |
| |       grid-based inventory system          | |
| |       with organizable tabs                | |
| |                                            | |
| |       weapons | armor | items | misc       | |
| |                                            | |
| |                                            | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Guild Bank tab displays the guild inventory.  <br>
Items will be shown in a grid layout and organized into categories such as weapons, armor, items, and miscellaneous items. <br>

### Screen 4: Members Tab
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| members                                        |
|                                                |
| +--------------------------------------------+ |
| |                                            | |
| |   scrolling roster of recruited guild      | |
| |   members                                  | |
| |                                            | |
| |   [ Member 1 ]                             | |
| |   [ Member 2 ]                             | |
| |   [ Member 3 ]                             | |
| |                                            | |
| |   tapping a member opens customization     | |
| |                                            | |
| |                                            | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Members tab allows the user to view all recruited guild members. Tapping a member opens their customization or detail panel. <br>

### Screen 5: Quests Tab
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| quests                                         |
|                                                |
| +--------------------------------------------+ |
| |                                            | |
| |   scrollable list of available quests      | |
| |                                            | |
| |   [ Quest 1 ]                              | |
| |   [ Quest 2 ]                              | |
| |   [ Quest 3 ]                              | |
| |                                            | |
| |   tapping a quest expands details, stat    | |
| |   requirements, rewards, and member slots  | |
| |                                            | |
| |                                            | |
| |                                            | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |

+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Quests tab displays available quests. The user can review quest details, requirements, rewards, and assign guild members to quest slots. <br>

### Screen 6: Recruitment Tab
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| recruitment                                    |
|                                                |
| +--------------------------------------------+ |
| |                                            | |
| |   as guild fame increases, more NPC        | |
| |   applicants become available              | |
| |                                            | |
| |   [ Applicant 1 ]                          | |
| |   [ Applicant 2 ]                          | |
| |   [ Applicant 3 ]                          | |
| |                                            | |
| |   view stats, traits, and preferences      | |
| |                                            | |
| |   [ Accept ]                [ Decline ]    | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Recruitment tab allows the user to review NPC applicants and choose whether to accept or decline them. Recruiting members helps the guild grow and complete harder quests. <br>

### Screen 7: Member Selection/Dispatch Screen
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| select member for quest                        |
|                                                |
| +--------------------------------------------+ |
| |                                            | |
| |   [ Member 1 ]                             | |
| |   stats: STR #  INT #  AGI #               | |
| |                                            | |
| |   [ Member 2 ]                             | |
| |   stats: STR #  INT #  AGI #               | |
| |                                            | |
| |   [ Member 3 ]                             | |
| |   stats: STR #  INT #  AGI #               | |
| |                                            | |
| |   [ Confirm Selection ]                    | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Member Selection screen appears when the user chooses a quest slot. It allows the user to select which guild member should be dispatched. <br>

### Screen 8: Member Customization Panel
```
+------------------------------------------------+
| guild_name          fame: #        currency: # |
+------------------------------------------------+
| member details                                 |
|                                                |
| +--------------------------------------------+ |
| | name: Member Name                          | |
| | class/type: Member Class                   | |
| | level: #                                   | |
| |                                            | |
| | stats:                                     | |
| | STR: #                                     | |
| | INT: #                                     | |
| | AGI: #                                     | |
| | WIS: #                                     | |
| |                                            | |
| | equipment:                                 | |
| | weapon: item name                          | |
| | armor: item name                           | |
| | accessory: item name                       | |
| |                                            | |
| | [ Customize ]              [ Back ]        | |
| |                                            | |
| +--------------------------------------------+ |
|                                                |
+------------------------------------------------+
| guild bank  |  members  |  quests  | recruit   |
+------------------------------------------------+
```
The Member Customization panel displays information about a selected guild member, including stats, level, class, and equipment. <br>

# Glossary
Incremental Game - An incremental game is a video game subgenre characterized by the incremental accumulation of in-game resources, and gradual, often exponential progression through repetitive actions or automation. <br>

MMO - A massively multiplayer online (MMO) game, sometimes referred to as an MMOG, is an online video game with a large number of players to interact in the same online game world. <br>

MMORPG - A massively multiplayer online role-playing game (MMORPG) is a video game that combines aspects of a role-playing video game and a massively multiplayer online game (see MMO).  <br>