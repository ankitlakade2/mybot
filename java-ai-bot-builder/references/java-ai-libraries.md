# Java AI Library Selection

## Quick comparison
- **LangChain4j**: Best for tool use, agents, and retrieval-augmented generation (RAG). Good defaults for prompt templates and memory.
- **OpenAI Java SDK**: Best for a lean, direct OpenAI integration without extra abstraction.
- **DJL (Deep Java Library)**: Best for local inference with on-device models and offline use.

## Selection guidance
1. **Need tools/agents/RAG?** Use LangChain4j.
2. **Only need to call OpenAI APIs?** Use the OpenAI Java SDK.
3. **Need offline or local model execution?** Use DJL.

## Minimal dependency notes (conceptual)
- LangChain4j: Core + provider-specific module (e.g., OpenAI).
- OpenAI Java SDK: Single SDK dependency.
- DJL: Core + engine/runtime for model execution.

Use these notes to pick the smallest viable dependency set for local development.
