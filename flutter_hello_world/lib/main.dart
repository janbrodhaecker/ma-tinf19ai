import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(title: "Say Hi!", home: MyState());
  }
}

class MyState extends StatefulWidget {
  @override
  MyStatefulWidget createState() => MyStatefulWidget();
}

class MyStatefulWidget extends State<MyState> {

  var myTextController = TextEditingController();

  void _sayHi() {
    print("Hi ${myTextController.text}!");
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: AppBar(title: Text("Hello world!")),
        body: Center(child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              width: 300,
              child: TextField(
                controller: myTextController,
                decoration: InputDecoration(hintText: "Say hi!"))),

            TextButton(onPressed: _sayHi, child: Text("Press me!"))
          ])));
  }
}
