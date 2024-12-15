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
	"if": "❓",
	"then": "⏩",
	"else": "⏬",
	"while": "🔄",
	"do": "🔼",
	"skip": "🔁",
	"true": "✅",
	"false": "❌",
	"print": "📝",
	"println": "📝⏬",
	"sleep": "😴",
	"separator": ""
}
```

### `example.jvs`:
```javascript
x <- 0 ;
🔄 ✅ 🔼 {
    y <- x ;
    🔄 1 < y 🔼 y <- y - 2 ;
    ❓ y = 0 ⏩ 📝⏬ x ⏬ 📝⏬ x * 2 ;
    😴 1000 ;
    x <- x + 1
}
```

## Installation and Usage

1. Compile the interpreter:
   ```bash
   mvn install
   ```
3. Run a JarvisScript program with a JSON configuration file:
   ```bash
   java -jar jarvis-script-VERSION-jar-with-depdendencies path/to/program.jvs path/to/config.json
   ```

## Use Cases

- **Educational Tool**: Explore how customizable syntax affects programming and computation.
- **Custom DSLs**: Build domain-specific languages by defining tailored syntax in a JSON file.
- **Creative Coding**: Experiment with expressive and unconventional programming styles.
