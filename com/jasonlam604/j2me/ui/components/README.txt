
History / Background:
==================================
Due to fact that J2ME comes with fairly poor UI support for forms elements  (referring to UPPER UI in MIDP 1.0 and MIDP 2) and this is before LWUIT was really of any use, I decided in my spare time to tinker around with hand painting components 
(I did a lot of this with the QuoteStream Wireless, all the grids and custom menus were hand pixel pushed).  Eventually, I had the
opportunity to use this code at CakePoker and at BCLC.  I do have to admit I did borrow bits of code off the web, ie: the image scale method in the ImageHolder class.
for the most part I would say approximately 80-90% of the code is mine with ideas borrowed from open source projects/public domain, basically I took what I thought was useful.
And of course I borrowed from well known common conventions like is component modal, dirty, tabstop, focus, visible...etc

Extra Notes:
===================================
1.  Please keep in mind J2ME is at java 1.18 ie: limited collections available like Vector, missing things like ArrayList
2.  All the classes that end with Listener are basically interfaces used as callbacks
3.  Component is the main class for all components like Label, TextBox (inherit 'Component')
4.  ComponentContainer is essentially the wrapper class for Canvas and contains the main logic to handle all the painting and triggers events like Left & Right soft key press
5.  oh yes, I did decided not to save bytes of data in certain places ie: I used setters and getters (ie: Component); its bit of waste for J2ME but I think its worth it considering this is
something that would be reused.  Oh, in case you are wondering if you haven't done J2ME dev, in earlier days there would be restrictions on jar size (ie: a certain Model from Nokia would have
a spec saying you can't exceed 70KB jar size) or restrictions from carrier on what OTA size limit would be or other weird stuff like Blackberry --> the files to be chunked in 64KB for OTA.
anyways doesn't really matter I guess now since Symbian is more or less dead and once RIM gets rid of the OS that supports J2ME IMO its more or less obsolete;
and MS OS takes over Nokia

Improvements needed:
===================================
1. Handle scrolling of components