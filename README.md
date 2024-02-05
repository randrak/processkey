# Java Directory Monitor

This Java application monitors a specified directory for new Java properties files. Upon detection of a new file, it processes the file according to the following rules:

1. **Read Properties**: Loads the properties file into a `Map` for easy access to the key-value pairs.
2. **Regex Filter**: Applies a regular expression pattern to filter the keys. Only the keys that match the pattern are retained along with their corresponding values.
3. **Data Relay**: Relays the filtered key-value mappings to a designated server program for further processing or storage.
4. **Cleanup**: Deletes the properties file from the directory after processing to maintain a clean workspace.

## Getting Started

(Here, you would include instructions on how to set up, configure, and run your program, including installing any necessary dependencies.)

## Configuration

(Explain how users can configure the directory to monitor, the regular expression for filtering keys, and details about the server program to relay data to.)

## Usage

(Provide a step-by-step guide on using the application, including any command-line arguments or configuration file settings.)

## Contributing

(Instructions for other developers on how they can contribute to the project.)

## License
This project is licensed under the GPL-3.0. See the LICENSE file for details.
