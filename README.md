# Java Directory Monitor

This Java application monitors a specified directory for new Java properties files. Upon detection of a new file, it processes the file according to the following rules:

1. **Read Properties**: Loads the properties file into a `Map` for easy access to the key-value pairs.
2. **Regex Filter**: Applies a regular expression pattern to filter the keys. Only the keys that match the pattern are retained along with their corresponding values.
3. **Data Relay**: Relays the filtered key-value mappings to a designated server program for further processing or storage.
4. **Cleanup**: Deletes the properties file from the directory after processing to maintain a clean workspace.

## Usage

To effectively utilize this application, please follow the detailed instructions provided in each project's README file.

## Contributing

For contributing to this project, we welcome your innovative solutions and improvements. When submitting contributions, please ensure your code adheres to the SOLID principles for maintainability and scalability. Document your changes thoroughly and provide tests where applicable. 


## License
This project is licensed under the GPL-3.0.
