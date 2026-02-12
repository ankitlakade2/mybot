---
name: java-ai-bot-builder
description: "Plan and design a local Java-based AI bot program, including architecture, dependency selection, and step-by-step build/run workflow. Use when a user asks for a Java bot plan, AI library selection, local setup, or an end-to-end workflow for building and running a Java AI bot on their machine."
---

# Java AI Bot Builder

## Overview
Create a complete, local-system plan and workflow for building a Java bot that uses an AI library, including library selection, project setup, run steps, and validation checkpoints.

## Workflow Decision Tree
1. **Clarify bot scope**: conversational bot, task automation, retrieval-augmented Q&A, or tool-using assistant.
2. **Choose AI library**: pick based on target LLM provider and features.
3. **Define local runtime**: Java version, build tool (Maven/Gradle), and environment variables.
4. **Design architecture**: components, data flow, and interfaces.
5. **Draft build/run workflow**: concrete local commands and file structure.
6. **Add testing/validation**: smoke test, prompt tests, and logging.

## Step 1: Gather Requirements
- Ask for the bot’s purpose, target platform (CLI, desktop, web, chat), and AI provider.
- Identify constraints: offline support, latency, cost limits, and data sensitivity.
- Define key flows (e.g., “user asks → bot replies → tool call → response”).
- Capture tone, persona, and command requirements if the bot is user-facing.

## Step 2: Select AI Library
- Use **LangChain4j** for agent workflows, tools, and RAG.
- Use **OpenAI Java SDK** if the user only needs a direct OpenAI integration.
- Use **DJL** (Deep Java Library) for on-device models and local inference.
- Record why the library fits the requirements.

See [references/java-ai-libraries.md](references/java-ai-libraries.md) for selection guidance and minimal dependency notes.

## Step 3: Define Local Project Setup
- Specify Java version (recommend 17+), build tool, and folder layout.
- Identify required environment variables (API keys, base URLs, model names).
- Provide commands to initialize the project and run it locally.

## Step 4: Design the Bot Architecture
- Outline components: input adapter, prompt builder, AI client, tool layer, memory/store.
- Specify data flow in bullet steps.
- List configuration files and where secrets live.
- If building a chat bot, include command routing and response formatting layers.

## Step 5: Produce the Local Build & Run Workflow
- Provide a step-by-step workflow with exact commands.
- Include “first-run smoke test” and expected output.

See [references/local-workflow.md](references/local-workflow.md) for a reusable local workflow template.
Use [assets/java-telegram-ai-bot](assets/java-telegram-ai-bot) as a Spring Boot starter codebase when the user needs a bot that analyzes job posts from URLs and tailors resumes.

## Step 6: Add Validation & Iteration
- Define test prompts and acceptance criteria.
- Add logging/telemetry notes for debugging.
- Provide an iteration loop: tweak prompts → rerun → compare outputs.

## Step 7: Apply Bot Persona & Command Rules (if provided)
- Translate user-supplied behavior rules into prompt/system instructions and command handlers.
- Validate concise response length, tone, and safe behavior.

See [references/telegram-community-bot.md](references/telegram-community-bot.md) for an example requirements set.

## Output Checklist
When responding to the user, include:
- A complete local plan and workflow.
- Library choice and rationale.
- Explicit build/run commands.
- Minimal file structure outline.
- Testing/validation steps.
- Persona/command requirements if the bot is user-facing.
