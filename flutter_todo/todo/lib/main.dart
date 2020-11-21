import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "Todo List",
      home: TodoWidget()
    );
  }
}

class TodoWidget extends StatefulWidget {
  @override
  createState() => new TodoList();
}

class TodoList extends State<TodoWidget> {

  List<String> todos = ["Programmierentwurf fertig stellen", "Einkaufen"];

  void _addNewTodoItem() {
    setState(() => todos.add("Item ${todos.length}"));
  }

  void _deleteItem(index) {
    setState(() => todos.removeAt(index));
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(title: Text('Todo List')),
      body: ListView.builder(
        itemBuilder: (context, index) {
          if (index < todos.length) {
            return ListTile(
              title: Text("${todos[index]}"),
              onTap: () => _deleteItem(index));
          }
        }),
      floatingActionButton: FloatingActionButton(
        onPressed: _addNewTodoItem, child: Icon(Icons.add)),
    );
  }

}