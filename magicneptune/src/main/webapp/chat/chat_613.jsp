<html> 
<head> 
   <title>Jetty chat</title> 
   <script type="text/javascript" src="../resources/scripts/behaviour.js"></script> 
   <script type="text/javascript" src="../resources/scripts/ajax.js"></script> 
   <script type="text/javascript" src="../resources/scripts/chat.js"></script> 
   <link rel="stylesheet" type="text/css" href="../resources/css/chat.css"></link> 
</head> 
<body> 
   <h1> 
    Jetty AJAX Chat 
   </h1> 
   Three really important things about this chat room demo: 
   <ul> 
    <li> 
     It has really HORRID Styling. Please feel free to donate a pretty 
     css :-) 
    </li> 
    <li> 
     It is written using js techniques provided by 
     <a href="http://bennolan.com/behaviour/">Behaviour</a> and 
    <li> 
     It uses Jetty6 
     <a href="/javadoc/org/mortbay/util/ajax/Continuation.html">Continuations</a>. 
     No threads are used when waiting for async events (see below). 
    </li> 
   </ul> 
   <div id="chatroom"> 
    <div id="chat"></div> 
    <div id="members"></div> 
    <div id="input"> 
     <div id="join"> 
      Username:&nbsp; 
      <input id="username" type="text" /> 
      <input id="joinB" class="button" type="submit" name="join" 
       value="Join" /> 
     </div> 
     <div id="joined" class="hidden"> 
      Chat:&nbsp; 
      <input id="phrase" type="text"></input> 
      <input id="sendB" class="button" type="submit" name="join" 
       value="Send" /> 
      <input id="leaveB" class="button" type="submit" name="join" 
       value="Leave" /> 
     </div> 
    </div> 
   </div> 
</body> 
</html> 