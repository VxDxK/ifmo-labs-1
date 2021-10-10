mkdir lab0
mkdir lab0/aron3
mkdir lab0/aron3/foongus
mkdir lab0/aron3/gurdurr
mkdir lab0/aron3/natu
mkdir lab0/aron3/flareon
mkdir lab0/aron3/axew
mkdir lab0/aron3/sudowoodo
echo "Живет Cave Freshwater" > lab0/dratini3
mkdir lab0/hitmontop8
echo -e "Способности Pure Blood\nMach Speed Inner Focus White Smoke" > lab0/hitmontop8/dragonite
mkdir lab0/hitmontop8/aron
echo "Тип диеты" > lab0/hitmontop8/salamence
echo "Carnivore" >> lab0/hitmontop8/salamence
mkdir lab0/hitmontop8/tyranitar
echo -e "Возможности Overland=10 Surface=7 Jump=3 Power=3\nIntelligence=4 Tracker=0" > lab0/linoone9
echo "Тип покемона GROUNG" > lab0/rhyhorn9
echo "ROCK" >> lab0/rhyhorn9
mkdir lab0/togepi9
touch lab0/togepi9/dragonite
echo -e "Способности Pure Blood\nMach Speed Inner Focus White Smoke" > lab0/togepi9/dragonite
mkdir lab0/togepi9/kricketot
mkdir lab0/togepi9/happiny
touch lab0/togepi9/tranquill
echo -e "Тип покемона\nNORMAL FLYING" > lab0/togepi9/tranquill
touch lab0/togepi9/swadloon
echo "weight=16.1 height=20.0 atk=6 def=9" > lab0/togepi9/swadlooncd lab0
cd lab0
chmod 750 aron3 
chmod 577 aron3/foongus
chmod 764 aron3/gurdurr
chmod 373 aron3/natu
chmod 752 aron3/flareon
chmod 511 aron3/axew
chmod 537 aron3/sudowoodo
chmod 444 dratini3
chmod 570 hitmontop8
chmod 006 hitmontop8/dragonite
chmod 753 hitmontop8/aron
chmod 004 hitmontop8/salamence
chmod 363 hitmontop8/tyranitar
chmod 620 linoone9
chmod a-rwx rhyhorn9; chmod u+r rhyhorn9; chmod o+r rhyhorn9
chmod 570 togepi9
chmod 400 togepi9/dragonite
chmod 755 togepi9/kricketot
chmod u+wx togepi9/happiny;chmod g+rwx togepi9/happiny;chmod o+wx togepi9/happiny
chmod 046 togepi9/tranquill
chmod 064 togepi9/swadlooncd lab0

chmod u+r hitmontop8/salamence;chmod u+r togepi9/swadloon
cat hitmontop8/salamence togepi9/swadloon > rhyhorn9_67
chmod u-r hitmontop8/salamence

chmod u+w togepi9
ln -s dratini3 togepi9/tranquilldratini

chmod u+w hitmontop8
ln linoone9 hitmontop8/dragonitelinoone
chmod u-w hitmontop8

cp dratini3 hitmontop8/aron

chmod u+r togepi9/happiny; chmod u+r togepi9/tranquill;chmod u+w hitmontop8/aron
cp -r -R -P togepi9 hitmontop8/aron
chmod u-r togepi9/swadloon

cat rhyhorn9 > togepi9/dragoniterhyhorn
chmod u-w togepi9

ln -s aron3 Copy_26cd lab0
wc -m linoone9 >> linoone9
ls -alR 2> /dev/null| grep "r$" | sort -r -k9
cat -n rhyhorn9 | sort -r -k2
cat -n echo hitmontop8/* 2> /tmp/opd | grep "on"
cat -n echo aron3/* 2> /dev/null | grep -v "gon"
ls -lR | grep "ron" | tail -n 2cd lab0
rm -f linoone9

chmod u+w togepi9/
rm -f togepi9/swadloon

rm -f Copy_26

chmod u+w hitmontop8
rm -f hitmontop8/dragonitelinoone
chmod u-w hitmontop8

rm -rf togepi9
rmdir aron3/flareon