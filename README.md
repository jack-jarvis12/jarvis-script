# JarvisScript

JarvisScript is an interpreted, Turing-complete programming language that enables fully customizable syntax by allowing you to define terminal symbols through a JSON configuration file.

## Key Features

- **Custom Terminal Symbols**: Use a JSON configuration file to define your own syntax for operators, keywords, and structure.
- **Turing Complete**: Supports all constructs necessary for universal computation.
- **Java-Based Interpreter**: Lightweight and portable, written in Java for cross-platform compatibility.

## Getting Started

1. Install the Java Development Kit (JDK) if you don’t have it already.
2. Clone the JarvisScript repository.
3. Define your terminal symbols in a JSON configuration file (e.g., `config.json`).

## Example

### `config.json`:
```json
{
  "plus": "+",
  "print": "System.out.println"
}
```

### `example.jvs`:
```javascript
print("Hello, World!")
int result = 5 plus 10
print(result)
```

## Installation and Usage

1. Clone the repository:
    ```bash
    git clone https://github.com/your-repo/jarvisscript.git
    cd jarvisscript
    ```

2. Compile the interpreter:
3. Write a JarvisScript program:
4. Run your JarvisScript program with the JSON configuration file:

## Use Cases

- **Educational Tool**: Explore how customizable syntax affects programming and computation.
- **Custom DSLs**: Build domain-specific languages by defining tailored syntax in a JSON file.
- **Creative Coding**: Experiment with expressive and unconventional programming styles.
