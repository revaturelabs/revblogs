function filter(){return clean=!0,$(".post-content").profanityFilter({customSwears:["2g1c","2 girls 1 cup","acrotomophilia","alabama hot pocket","anal","anilingus","anus","arse","arsehole","ass","asshole","assmunch","auto erotic","autoerotic","babeland","baby batter","ball gag","ball gravy","ball kicking","ball licking","ball sack","ball sucking","bangbros","bareback","barely legal","barenaked","bastardo","bastinado","bbw","bdsm","beaver cleaver","beaver lips","bestiality","bi curious","big black","big breasts","big knockers","big tits","bimbos","birdlock","bitch","black cock","blonde action","blonde on blonde action","blow j","blow your l","blue waffle","blumpkin","bollocks","bondage","boner","boob","boobs","booty call","brown showers","brunette action","bukkake","bulldyke","bullet vibe","bung hole","bunghole","busty","butt","buttcheeks","butthole","camel toe","camgirl","camslut","camwhore","carpet muncher","carpetmuncher","chocolate rosebuds","circlejerk","cleveland steamer","clit","clitoris","clover clamps","clusterfuck","cock","cocks","coprolagnia","coprophilia","cornhole","cum","cumming","cunnilingus","cunt","darkie","date rape","daterape","deep throat","deepthroat","damn","dick","dildo","dirty pillows","dirty sanchez","dog style","doggie style","doggiestyle","doggy style","doggystyle","dolcett","domination","dominatrix","dommes","donkey punch","double dong","double penetration","dp action","eat my ass","ecchi","ejaculation","erotic","erotism","escort","ethical slut","eunuch","faggot","fecal","felch","fellatio","feltch","female squirting","femdom","figging","fingering","fisting","foot fetish","footjob","frotting","fuck","fuck buttons","fudge packer","fudgepacker","futanari","g-spot","gang bang","gay sex","genitals","giant cock","girl on","girl on top","girls gone wild","goatcx","goatse","gokkun","golden shower","goo girl","goodpoop","goregasm","grope","group sex","guro","hand job","handjob","hard core","hardcore","hentai","homoerotic","honkey","hooker","hot chick","how to kill","how to murder","huge fat","humping","incest","intercourse","jack off","jail bait","jailbait","jerk off","jigaboo","jiggaboo","jiggerboo","jizz","juggs","kike","kinbaku","kinkster","kinky","knobbing","leather restraint","leather straight jacket","lemon party","lolita","lovemaking","make me come","male squirting","masturbate","menage a trois","milf","missionary position","motherfucker","mound of venus","mr hands","muff diver","muffdiving","nambla","nawashi","negro","neonazi","nig nog","nigga","nigger","nimphomania","nipple","nipples","nsfw images","nude","nudity","nympho","nymphomania","octopussy","omorashi","one cup two girls","one guy one jar","orgasm","orgy","paedophile","panties","panty","pedobear","pedophile","pegging","penis","phone sex","piece of shit","piss pig","pissing","pisspig","playboy","pleasure chest","pole smoker","ponyplay","poof","poop chute","poopchute","porn","porno","pornography","prince albert piercing","pthc","pubes","pussy","queef","raghead","raging boner","rape","raping","rapist","rectum","reverse cowgirl","rimjob","rimming","rosy palm","rosy palm and her 5 sisters","rusty trombone","s&m","sadism","scat","schlong","scissoring","semen","sex","sexo","sexy","shaved beaver","shaved pussy","shemale","shibari","shit","shota","shrimping","slanteye","slut","smut","snatch","snowballing","sodomize","sodomy","spic","spooge","spread legs","strap on","strapon","strappado","strip club","style doggy","suck","sucks","suicide girls","sultry women","swastika","swinger","tainted love","taste my","tea bagging","threesome","throating","tied up","tight white","tit","tits","titties","titty","tongue in a","topless","tosser","towelhead","tranny","tribadism","tub girl","tubgirl","tushy","twat","twink","twinkie","two girls one cup","undressing","upskirt","urethra play","urophilia","vagina","venus mound","vibrator","violet wand","vorarephilia","voyeur","vulva","wank","wet dream","wetback","white power","women rapping","wrapping men","wrinkled starfish","xx","xxx","yaoi","yellow showers","yiffy","zoophilia"],profaneText:function(a){alert("Please change the highlighted words."),clean=!1}}),clean}!function(a){"use strict";String.prototype.repeat=function(a){return new Array(a+1).join(this)},Array.prototype.unique=function(){var a,b,c;for(a=this.concat(),b=0;b<a.length;++b)for(c=b+1;c<a.length;++c)a[b]===a[c]&&a.splice(c,1);return a};var b={replaceWith:"*",customSwears:null,externalSwears:null,filter:!0,profaneText:function(){}};a.fn.profanityFilter=function(c,d){function g(a){function b(a){var b,c=[];for(b=0;b<a.childNodes.length;b+=1)c[b]=a.childNodes[b];return c}for(var c,d=[],e=b(a);e.length;)c=e.shift(),1===c.nodeType&&e.unshift.apply(e,b(c)),3===c.nodeType&&d.push(c);return d}function h(a){var b=new XMLHttpRequest;b.open("GET",a,!1),b.setRequestHeader("X-Requested-With","XMLHttpRequest"),b.send(null);try{return JSON.parse(b.responseText)}catch(a){return""}}function j(a){var b=Math.floor(Math.random()*a);return null==i?i=b:b==i&&0!=a&&(b+=1),b>a&&(b=0),i=b,b}var f,e=a.extend({},b,c);f=function(){var b,a=new Date;try{return localStorage.setItem("uid",a),b=localStorage.getItem("uid")==a,localStorage.removeItem("uid"),b&&localStorage}catch(a){}}();var i=null;return this.each(function(){var b,c,i,k,l,d=g(this),m=a(this).find(":input"),n=!1,o=[],p="localSwears"+e.externalSwears;if(null!==e.externalSwears){if(f){var q=localStorage.getItem(p);null===q&&(q=JSON.stringify(h(e.externalSwears)),localStorage.setItem(p,q)),b=JSON.parse(q)}else b=h(e.externalSwears);null!==e.customSwears&&(b=b.concat(e.customSwears).unique())}else null!==e.customSwears&&(b=e.customSwears);if(null!==b){for(c=0;c<b.length;c+=1){i=new RegExp("\\b"+b[c]+"\\b","gi");var r=j(e.replaceWith.length-1);for(k=e.replaceWith[r],"string"==typeof e.replaceWith&&(k='<span class="highlight">'+b[c]+"</span>"),l=0;l<d.length;l+=1)i.test(d[l].nodeValue)&&(n=!0,o.push(b[c]),e.filter&&a(".post-content").html(function(a,b){return b.replace(i,k)}));for(var l=0;l<m.length;l++)i.test(m[l].value)&&(n=!0,o.push(b[c]),e.filter&&a(m[l]).val(m[l].value.replace(i,k)))}n&&e.profaneText(o.unique())}})}}(jQuery),$(document).ready(filter());