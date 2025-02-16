# ⚡ Multi-threading & Multi-processing in Operating Systems

## 📌 Project Overview
This project demonstrates the use of **parallel programming** by implementing **multi-threading** and **multi-processing** to efficiently process large datasets. The main goal is to compare execution times and performance across different parallel computing methods using **Java**.

## 🎯 Objectives
- ✅ Implement **Naïve, Multi-threading, and Multi-processing** approaches.
- ✅ Compare **execution times** and efficiency of each method.
- ✅ Analyze speedup using **Amdahl's Law**.
- ✅ Determine the **optimal number of threads/processes** for best performance.

---

## ⚙️ Implementation Details

### **1️⃣ Naïve Approach**
- Sequentially processes the dataset without parallelism.
- Uses a **single thread** to iterate over the dataset.
- Serves as a baseline for performance comparison.

### **2️⃣ Multi-processing Approach**
- Uses **Java’s Fork/Join Framework** (`RecursiveTask`).
- Splits the dataset into chunks, each handled by a **separate process**.
- Tasks are executed in parallel using a `ForkJoinPool`.
- **Pros:** Efficient for CPU-intensive tasks.
- **Cons:** High overhead due to process creation & inter-process communication.

### **3️⃣ Multi-threading Approach**
- Uses **ExecutorService** & `Callable` for parallel execution.
- Dataset is divided among multiple **threads** (using `FixedThreadPool`).
- **Pros:** Lower overhead & faster execution compared to multiprocessing.
- **Cons:** Less effective for completely independent tasks.

---

## 🏗️ Performance Analysis

### **🔢 Speedup Calculation (Amdahl’s Law)**
- Speedup formula:
  ```
  Speedup = Execution_Time_Old / Execution_Time_New
  ```
- **Naïve Execution Time**: `1644 ms`
- **Best Multi-threaded Execution Time**: `255 ms` (8 threads)
- **Speedup Achieved**: `6.45x`
- **Maximum Theoretical Speedup (8 cores)**: `6.61x`

### **💡 Optimal Number of Processes & Threads**
- **Multi-threading:** Best performance at **8 threads** (matches the system’s 8-core CPU).
- **Multi-processing:** Best performance at **6 processes** (overhead increases beyond this point).

---

## 🚀 Getting Started

### 🔧 Prerequisites
Ensure you have the following installed:
- **Java 8+**
- **IntelliJ IDEA or any Java IDE**

### 📂 Cloning the Repository
```sh
git clone https://github.com/mohammadkhdour/Parallel-Computing-OS.git
cd Parallel-Computing-OS
```

### ▶️ Running the Project
1. Compile the Java files:
   ```sh
   javac Main.java
   ```
2. Run the program:
   ```sh
   java Main
   ```

---

## 📊 Results & Insights
| Approach | Execution Time |
|----------|---------------|
| Naïve | 1644 ms |
| Multi-processing (6 processes) | 425 ms |
| Multi-threading (8 threads) | 255 ms |

- **Multi-threading is the fastest approach** for this workload.
- **Multi-processing peaks at 6 processes** before increasing in overhead.
- **Parallelism effectively reduces execution time**, but the optimal method depends on workload type.

---

## 🤝 Contributing
Contributions are welcome! Fork this repository and submit a **Pull Request**.



