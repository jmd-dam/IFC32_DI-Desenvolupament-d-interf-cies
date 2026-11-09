# CIFP PAU CASESNOVES · HIGHER VET IN DAM

# Java Swing Practical Assignment

# Video Game Rental Management Application

| INSTITUTION | COURSE & CYCLE | TECHNOLOGY |
| --- | --- | --- |
| CIFP Pau Casesnoves | UI Development · GS DAM | Java Swing & NetBeans |

## 1. ASSIGNMENT OBJECTIVE

Develop a desktop application using **Java Swing** and **NetBeans** to manage users, video games, and rentals. The interface must be functional, consistent, and ready to demonstrate a complete operational workflow.

This assignment focuses on local data management during runtime; an external database is not required unless explicitly specified.

## 2. MAIN WINDOW AND NAVIGATION MENU

The application must feature a main window equipped with a menu bar providing access to rental, user, and game management options. The main window must display a rentals table containing at least:

| Field | Column Description | Example Status & Color Coding |
| --- | --- | --- |
| User | Name of the user associated with the rental | Marc Rosselló |
| Game | Title of the requested video game | The Legend of Zelda |
| Platform | Specifically assigned platform | Switch 2 |
| Start Date | Rental start date (automatically assigned) | 23/10/2026 |
| Expected Return | Return deadline (automatically assigned) | 30/10/2026 |
| Rental Status | Status with visual color highlight per rules | Returned (Green) Overdue (Red) |

The main window must also present clear action controls for recording a rental and registering a return. Following every operation, both the table and platform availability numbers must update immediately.

## 3. RENTAL REGISTRATION AND RETURNS

The rental registration form must allow selecting a user, a game, and a platform. It can be pre-opened with a user and a game already selected when accessed directly from the main window.

- Date Handling: Start and return dates must be assigned automatically and cannot be manually edited by the user.

- Dynamic Availability: Only platforms with available stock for the selected game may be selected. Radio buttons must be disabled if no units are available for that platform.

- Rental Impact: Registering a rental must deduct one unit from the selected platform stock and log the transaction.

- Return Impact: Registering a return must update the rental status (marked in green) and restore the unit back to platform stock.

CIFP Pau Casesnoves — UI Development (DAM) Page 1 of 3

## 4. USER AND GAME MANAGEMENT

#### 👤 User Management

- Create users with necessary identification fields (name, contact information).

- Delete a user only if they have no active rentals.

- Display an explanatory warning message if deletion is denied.

- Refresh selection lists and tables immediately after creating or deleting users.

#### 🎮 Game Management

- Create games providing basic details and platform-specific stock counts.

- Minimum required platforms: PS5 , Switch 2 , Xbox , PC .

- Delete a game only if it has no active rentals. Inform user if blocked.

- Platform-isolated stock: units available on one platform do not imply availability on another.

## 5. BUSINESS RULES AND USABILITY GUIDELINES

To guarantee system integrity and high usability, the following domain rules must be strictly enforced:

- Visual Highlight Rules: In the main window table, rentals past their expected return date must be highlighted in red. Returned rentals must be highlighted in green.

- Stock Constraints: Non-existent games or platforms without available stock cannot be rented.

- Single Rental State: Active rentals cannot be duplicated or processed as returned until an explicit return is registered.

- Referential Integrity: Users or games with active rentals cannot be deleted under any circumstances.

- System Synchronization: Every addition, deletion, rental, or return must keep all UI lists, tables, and stock counters synchronized.

- Input Validation: Required fields and selection controls must be validated before confirming actions.

#### 💡 User Interface Usability Standards

The interface must utilize understandable labels, clear action-oriented buttons, and informative dialogs for errors and confirmations. Windows must maintain a clean layout with consistent margins, titles, alignments, and standard component sizing. Controls that allow invalid operations must be disabled (especially platform radio buttons). Navigation must enable task completion e fficiently without redundant steps.

## 6. TECHNICAL REQUIREMENTS AND LOCALIZATION

- Architecture: Develop the application in Java using Java Swing and NetBeans, cleanly separating data logic from UI components as much as possible.

- Data Structures: Utilize appropriate data structures to manage users, games, platforms, inventory, and rentals in memory. Handle expected exceptions gracefully without app crashes. Maintain clean, readable code with descriptive identifier names.

- Application Language & Locale: The user interface must be fully provided in Catalan. All menus, buttons, labels, and message dialogs must be localized using a resource bundle file named Bundle_ca with the ca-ES locale.

- Validation Library: Build a custom library named ValidationAPI to centralize form data validations.

#### ⚠ Name Validation via ValidationAPI

The ValidationAPI library must implement mandatory form input rules. User names **must start with an uppercase** **letter.** If a name fails this rule: form submission must be blocked, an informative error message in Catalan must be displayed, and the dialog must explicitly state that names must begin with a capital letter.

CIFP Pau Casesnoves — UI Development (DAM) Page 2 of 3

## 7. MANDATORY INITIAL TEST DATA

Upon launch, the application must automatically pre-load initial data to allow immediate testing of all features:

#### 👥 Initial Users & Games 📋 Initial Rentals

- Two distinct users. Two initial rentals (one per user).

- Game 1: Available for PS5 and Switch 2. Each rental consumes its respective platform stock.

- Game 2: Available for Xbox and PC. State accurately reflects active inventory.

## 8. VERSION CONTROL AND ASSESSMENT

Create and maintain a public **GitHub** repository for the project. Primary version management must be executed through NetBeans: file tracking, commit management, and remote synchronization. Make meaningful commits throughout development; avoid delivering a single final commit. Include a concise README explaining build, execution, and testing procedures alongside initial dataset details. *Follow-up & Evaluation:* The instructor may inquire about code implementation, design choices, or runtime behavior during class sessions. Students must be capable of explaining and demonstrating their codebase.

## 9. PRE-SUBMISSION CHECKLIST

✔ The project opens, builds, and executes properly inside NetBeans.

✔ The GitHub repository is accessible and displays a commit history managed via NetBeans.

✔ The README is updated with execution instructions and initial test data description.

✔ Mandatory initial data loads automatically at application startup.

✔ Rental and return workflows are verified, including platform-specific inventory deduction.

✔ In the main table, overdue rentals appear in **red** and returned rentals in **green.**

✔ The interface is entirely localized in Catalan, including all error dialogs.

✔ Both ValidationAPI and Bundle_ca ( ca-ES ) exist and function correctly. ✔ Form inputs reject user names that do not start with a capital letter. ✔ Disabled state prevents invalid button clicks or stock-out platform selection.

## 10. EVALUATION CRITERIA AND FINAL NOTES

**Evaluation Domain Key Assessed Aspects** **Core Functionality** Handling of users, games, rentals, returns, and platform-specific stock counts. **Validation & Locale** Name validation via ValidationAPI, deletion constraints, and full translation with Bundle_ca. **Usability & Design** Clarity, visual balance, consistency, and intuitive user navigation throughout windows. **Code Quality & Git** Code organization, exception handling, and proper NetBeans/GitHub integration with commits. *Final considerations:* Do not add features that contradict core requirements. Test edge cases prior to submission: depleted stock, unavailable platforms, returns, attempting to delete active entities, and non-capitalized name inputs. The deliverable must be fully executable and ready for demonstration.

CIFP Pau Casesnoves — User Interface Development (MAD) Page 3 of 3