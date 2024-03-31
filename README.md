# Cryptography Assignment: Tess of the d'Urbervilles

This repository contains my solutions for the Cryptography Assignment from the COMP5580 course at the University of Kent. The assignment involves decrypting extracts from the novel "Tess of the d'Urbervilles" by Thomas Hardy, which are encoded using various cryptographic techniques.

## Usage instructions
1. Make a folder similar to **/resources/users/_aragorn**, with the following paths:
   - /resources/users/<YOUR_USERNAME>/input/cexercise1.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise2.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise3.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise4.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise5.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise6.txt
   - /resources/users/<YOUR_USERNAME>/input/cexercise7.txt
1. Enable the exercises you want to run in the **AppConfig.java** file at project root.
2. Compile & Run **Launcher.java** at project root, as a java file.
3. Check the output at **/resources/users/<YOUR_USERNAME>/output/** for the decrypted texts.

## Assignment Overview

The assignment consists of seven exercises, each involving a different cipher or encryption method:

1. ✅ **Exercise 1**: Decrypt a plaintext encoded with a Caesar cipher.
2. ✅ **Exercise 2**: Decrypt a plaintext encoded with a Vigenere cipher using the key "TESSOFTHEDURBERVILLES".
3. ✅ **Exercise 3**: Decrypt a plaintext encoded with a Vigenere cipher using an arbitrary 6-letter key.
4. ✅ **Exercise 4**: Decrypt a plaintext encoded with a Vigenere cipher using an arbitrary 4-6 letter key.
5. ✅ **Exercise 5**: Decrypt a plaintext encoded with a transposition cipher written row-wise across 4-6 columns.
6. ✅ **Exercise 6**: Decrypt a plaintext encoded with a transposition cipher written row-wise across 6 columns with an arbitrary column order.
7. ✅ **Exercise 7**: Decrypt a plaintext encoded with a general substitution cipher.

## Resources

The assignment provided the following resources:

- `cexercise1.txt`: A ciphertext encoded with a Caesar cipher.
- `cexercise2.txt`: A ciphertext encoded with a Vigenere cipher using the key "TESSOFTHEDURBERVILLES".
- `cexercise3.txt`: A ciphertext encoded with a Vigenere cipher using an arbitrary 6-letter key.
- `cexercise4.txt`: A ciphertext encoded with a Vigenere cipher using an arbitrary 4-6 letter key.
- `cexercise5.txt`: A ciphertext encoded with a transposition cipher written row-wise across 4-6 columns.
- `cexercise6.txt`: A ciphertext encoded with a transposition cipher written row-wise across 6 columns with an arbitrary column order.
- `cexercise7.txt`: A ciphertext encoded with a general substitution cipher.

- `tess.txt`: The complete ASCII text of "Tess of the d'Urbervilles" from Project Gutenberg.
- `tess27.txt`: A reduced version of the text with a 27-character alphabet.
- `tess26.txt`: A further reduced version with a 26-character alphabet.

## License

This repository is licensed under the [MIT License](LICENSE).

## Attribution:
- [F12-Syntex's Crypto Assignment Repo](https://github.com/F12-Syntex/TessCryptExercises)