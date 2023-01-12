-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1:3307
-- 產生時間： 2023-01-05 14:00:59
-- 伺服器版本： 10.4.14-MariaDB
-- PHP 版本： 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `group5_final`
--

-- --------------------------------------------------------

--
-- 資料表結構 `administrator`
--

CREATE TABLE `administrator` (
  `administrator_id` int(50) NOT NULL,
  `administrator_name` varchar(30) NOT NULL,
  `administrator_email` varchar(100) NOT NULL,
  `administrator_modified` datetime NOT NULL,
  `administrator_created` datetime NOT NULL,
  `administrator_isRoot` int(11) NOT NULL,
  `administrator_isDeleted` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `administrator`
--

INSERT INTO `administrator` (`administrator_id`, `administrator_name`, `administrator_email`, `administrator_modified`, `administrator_created`, `administrator_isRoot`, `administrator_isDeleted`) VALUES
(1, '林子恩', '109403048@gmail.com', '2020-01-01 13:03:00', '2020-01-01 13:03:00', 1, 0),
(2, '林采璇', '109403533@gmail.com', '2022-01-03 12:01:00', '2020-01-01 08:31:00', 0, 1),
(3, '洪琬哲', '109403535@gmail.com', '2022-01-04 12:02:00', '2020-01-02 09:43:00', 0, 1),
(4, '江珮彤', '109403538@g.ncu.edu.tw', '2020-01-05 10:49:00', '2020-01-05 10:49:00', 0, 0),
(5, '賴立彬', '109403543@g.ncu.edu.tw', '2020-01-06 12:38:00', '2020-01-06 12:38:00', 0, 0),
(6, '謝諶煜', '109403546@g.ncu.edu.tw', '2020-01-01 11:46:00', '2020-01-01 11:46:00', 1, 0);

-- --------------------------------------------------------

--
-- 資料表結構 `administratorcredential`
--

CREATE TABLE `administratorcredential` (
  `administratorcredential_id` int(50) NOT NULL,
  `administrator_id` int(50) NOT NULL,
  `administrator_hashedpassword` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `administratorcredential`
--

INSERT INTO `administratorcredential` (`administratorcredential_id`, `administrator_id`, `administrator_hashedpassword`) VALUES
(1, 1, '$2a$12$Cb603suW0FofTR0iZlz4pOBSep6Vi7Gq4QNyax3rWZyw1QnsqY4ju'),
(2, 2, '$2a$12$Y5WaPNObNywFmJHOQQF5nun8msnUrhETI2o7OIgbaD2zgVpPn7Vrq'),
(3, 3, '$2a$12$yN210Ap3rDrLTxNJx3ng1OnAij8iHZy8uHdeiP0LcULTdXNASjcpS'),
(4, 4, '$2a$12$CuzwLDFtbZHsTxWy03gGxOFouh0UfKE0Qo7wCTZpYr/J/WMI4kzjW'),
(5, 5, '$2a$12$y0EfYIqSd4j4ljVRT9o9a.z0bDXOoosi.A6YsrLvR.5xdkEeZJRxG'),
(6, 6, '$2a$12$wIQkkovoaNYjzyv9hl1T8.Fjtk51CPH69GmV7RuNazYlJMGFTtT4i');

-- --------------------------------------------------------

--
-- 資料表結構 `applystatus`
--

CREATE TABLE `applystatus` (
  `applystatus_id` int(11) NOT NULL,
  `applystatus_description` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `applystatus`
--

INSERT INTO `applystatus` (`applystatus_id`, `applystatus_description`) VALUES
(1, '報名成功'),
(2, '取消報名'),
(3, '刪除紀錄');

-- --------------------------------------------------------

--
-- 資料表結構 `collection`
--

CREATE TABLE `collection` (
  `collection_id` int(50) NOT NULL,
  `event_id` int(50) NOT NULL,
  `member_id` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `collection`
--

INSERT INTO `collection` (`collection_id`, `event_id`, `member_id`) VALUES
(1, 1, 1),
(2, 3, 1),
(3, 4, 3),
(4, 3, 5),
(5, 3, 6),
(6, 1, 8),
(7, 3, 8),
(8, 4, 8);

-- --------------------------------------------------------

--
-- 資料表結構 `event`
--

CREATE TABLE `event` (
  `event_id` int(50) NOT NULL,
  `event_title` varchar(50) NOT NULL,
  `eventtype_id` int(20) NOT NULL,
  `event_introduction` varchar(500) NOT NULL,
  `event_place` varchar(100) NOT NULL,
  `event_start_date` datetime NOT NULL,
  `event_end_date` datetime NOT NULL,
  `event_notification` varchar(300) DEFAULT NULL,
  `event_image` char(250) DEFAULT NULL,
  `event_isOutDated` int(11) NOT NULL,
  `event_isCanceled` int(11) NOT NULL,
  `event_modified` datetime NOT NULL,
  `event_created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `event`
--

INSERT INTO `event` (`event_id`, `event_title`, `eventtype_id`, `event_introduction`, `event_place`, `event_start_date`, `event_end_date`, `event_notification`, `event_image`, `event_isOutDated`, `event_isCanceled`, `event_modified`, `event_created`) VALUES
(1, '人文與科學的對話——「黑洞：科學、哲學與文學藝術」座談會', 1, '「黑洞」是天文學中備受矚目的議題，伴隨第一、二張黑洞影像的曝光，更掀起了各方面的關注。然而，「黑洞」不僅在科學領域熱門，在人文藝術的世界中，也出現不少與「黑洞」相關的作品，展現著人類對「黑洞」的另一種認知。\r\n\r\n繼《小行星的故事》、「挺進南北極」後，人文藝術中心展開新一波「人文與科學的對話」，探討「黑洞：科學、哲學與文學藝術」，邀請科教中心朱慶琪主任、天文所陳文屏教授、歷史所蔣竹山教授、中文系李欣倫教授、詩人白靈、編劇林孟寰，從新詩、電影、戲劇、歌曲等角度，探索人文領域「黑洞」意象。', '107電影院（人文社會科學大樓一樓）', '2022-12-20 10:00:00', '2022-12-20 12:00:00', '1.會後提供便當\r\n2.全程參與之學生可獲得人文藝術時數2小時\r\n3.全程參與之職工同仁可獲得學習時數2小時', 'statics/img/speech_1.png', 0, 0, '2022-12-01 12:13:00', '2022-12-01 12:13:00'),
(2, '中心的邊緣－蔡孟閶個展', 2, '此次個展，整理藝術家蔡孟閶自2016至2022年之部分作品，各時期皆有不同描繪主題，然而綜觀下來，這幾年一直都有一種「中心/邊緣」的觀照，企圖從不同系列不同主題間，找出一種可以涵括在近幾年的創作中所關注的焦點：所謂的中心，是否也有隱藏著的邊緣性格?\r\n\r\n人類居所與活動空間，從小聚落到大都市，一直是處於發展狀態，邊界與熱點也會隨時間不斷的更替、推移。蔡孟閶的創作從探討人們在城市如何生存、繪製逝去的家之記憶，一直到正在發展的「都會沒有鐵路」關注鐵路在都會區進行中的立體化工程，將改變或消失的風景。畫面總是透過橫幅、垂直水平、以及幾乎沒有景深與透視的構圖，經由刻畫光影或殘缺斑駁的鏽蝕，凝視在快速發展與先進化過程中，那些被擠壓或掉出的殘屑，不被關注也不被記憶，但的確存在著情感的客體。', '中央大學藝文中心', '2022-09-27 10:00:00', '2022-10-29 17:00:00', '開放時間為周二至周六', 'statics/img/art_1.jpg', 1, 0, '2022-09-20 10:39:00', '2022-09-20 10:39:00'),
(3, '中央大學校慶音樂會「台北愛樂交響樂團」', 3, '曲目\r\n莫札特：歌劇「費加洛婚禮」序曲\r\n小約翰‧史特勞斯：藍色多瑙河\r\n林京美：月亮代表我的心　(小提琴獨奏/蘇顯達)\r\n莫利克奈：新天堂樂園　(小提琴獨奏/蘇顯達)\r\n薩拉沙泰：流浪者之歌　(小提琴獨奏/蘇顯達)\r\n羅西尼：《威廉泰爾》序曲\r\n巴德爾特：《神鬼奇航》組曲\r\n艾爾加：第一號威風凜凜進行曲\r\n柴可夫斯基：1812序曲\r\n中央大學校歌\r\n\r\n樂團簡介\r\n台北愛樂交響樂團承襲古典音樂七十多年來在台灣的紮根滋養，集結優秀青年音樂人才，組成專業的樂團，團員們來自台灣樂壇的菁英，是最具臺灣新生代代表之交響樂團，首席音樂家們足跡踏遍世界各地國家演奏，在國際性受到高度榮耀與肯定，不僅在台灣的音樂史上留下重要紀錄，也獲得國內外重量級樂評肯定。\r\n「台北」是我們的土地、「愛樂」是我們的理想、「交響」是我們的途徑、「樂團」是我們的同心，TPSO台北愛樂交響樂團將用每一顆音符訴說情感、每一顆休止符聆聽社會需求，走進您生活中的音樂——「古典音樂流行化、流行音樂交響化～說故事的交響樂」', '中央大學大禮堂', '2022-12-16 19:30:00', '2022-12-16 21:00:00', '', 'statics/img/music_1.jpg', 0, 0, '2022-11-01 11:45:00', '2022-11-01 11:45:00'),
(4, '2022歐洲電影節：邊界．無界', 4, '終於慢慢解封，終於可以進電影院看來自歐洲各國的電影。\r\n今年的歐洲電影節，帶你跨出邊界。\r\n還沒計畫出國的你，先走進107電影院看TEFF 歐洲影展 Taiwan European Film Festival，看看各地發生的故事……', '107電影院（人文社會科學大樓一樓）', '2022-11-22 10:10:00', '2022-12-22 18:00:00', '1.本季影展皆有映後分享，全程參與者可獲得中大護照時數。\r\n2.本季影展放映前15分鐘開放免費入場，座位有限，額滿為止。\r\n3.防疫期間請觀影者全程配戴口罩，並配合防疫相關措施。\r\n4.本影院內禁止飲食，請勿攜帶飲料與食物進場。\r\n5.電影放映中，禁止拍照、錄影、錄音。\r\n6.活動若有變動，以本中心網頁或現場公告為準。', 'statics/img/movie_1.jpg', 0, 0, '2022-12-07 15:28:00', '2022-11-18 09:36:00'),
(5, '動畫手翻書 X 實驗停格動畫 工作坊', 5, '11/14 動畫手翻書工作坊\r\n回歸最單純的動畫創作形式，只需要一本小冊子和熱愛動畫、直覺的心，便能夠不經過任何數位方法，運用翻頁和視覺暫留的特性，完成屬於自己的動畫。透過課堂討論與媒材實驗，體驗不同種類的手繪材料，在連續圖中所產生的化學效果，從中體驗動畫製作的過程與樂趣。\r\n\r\n11/26 實驗停格動畫工作坊\r\n帶領同學們打破對動畫的既有想象，認識實驗動畫精神，並以各種形式的實驗動畫為實例，介紹實驗動畫的幕後技術與美學風格。實驗動畫（Experimental Animation）帶有對未知的探索與不斷嘗試的精神，同學們在過程可盡情利用各式隨手可得的媒材和多樣的創作方式去體驗動畫表現形式的新大陸，脫離框架任由個人天馬行空地實現腦內風景與絢麗想像力，並從中了解到動畫本質充滿無限可能性。', '依場次公告', '2022-11-14 15:00:00', '2022-11-26 17:00:00', '課程要求：\r\n11/14【動畫手翻書工作坊】\r\n1.文具、白紙（發想用）\r\n2.美術用品：4B鉛筆、色鉛筆、彩色筆、剪刀\r\n\r\n11/26【實驗停格動畫工作坊】\r\n1.請每位同學事先在手機安裝停格動畫軟體【Stop-motion studio】\r\n2.請大家事先在校園内采集5-10種植物（新鮮/枯萎都可，盡量保持乾燥不要潮濕）\r\n3.文具、白紙（發想用）', 'statics/img/workshop_1.jpg', 1, 0, '2022-11-01 14:19:00', '2022-11-01 14:19:00'),
(6, '2022「樂煞人也麼哥──第二屆大專崑曲聯演」', 6, '【再一次，快樂的不得了】\r\n去年11月，崑曲博物館主辦了第一次大專院校崑曲聯演「樂煞人也麼哥」，邀請了台大、東吳、中大、成大及戲曲學院共襄盛舉，學生們的積極及努力，在周六下午，獲得了滿滿的掌聲肯定。\r\n\r\n而在經歷了近一年的籌備後，我們很高興的宣布，「樂煞人也麼哥」又來了\r\n除了台大、中央、東吳、成大等等老朋友外，我們也很高興師大崑曲社今年也加入聯演的行列。小小編發文的當下，各校正準備迎來對腔、響排等等緊鑼密鼓的排練行程。\r\n所以，請進劇場來支持他們吧！', '黑盒子劇場', '2022-11-19 14:00:00', '2022-11-19 17:00:00', '1.因場地有限，報名後請待主辦單位寄送「報名成功通知函」，方完成報名程序。\r\n2.報名成功者請於13：50前入場，13：50後將視座位情況，開放現場候補入場。', 'statics/img/kunqu_1.jpg', 1, 0, '2022-11-02 10:04:00', '2022-11-01 15:31:00'),
(7, '人文與科技對話：挺進南北極', 1, '演講邀請太空系林映岑助理教授、地科系郭陳澔教授及波蘭籍張文和助理教授談他們在南北極進行研究的歷程。另有一場座談，邀請畫家楊恩生、旅行家鄭有利和眼科醫生朱建銘座談，分享他們多次探訪南北極的所見所聞，並由作家李欣倫進行觀察報告。', '中大教研大樓羅家倫講堂（教研大樓1樓）', '2021-12-17 14:00:00', '2021-12-17 17:00:00', '', 'statics/img/speech_2.png', 0, 1, '2021-12-14 08:56:00', '2021-12-01 15:12:00');

-- --------------------------------------------------------

--
-- 資料表結構 `eventsessions`
--

CREATE TABLE `eventsessions` (
  `eventsessions_id` int(50) NOT NULL,
  `event_id` int(50) NOT NULL,
  `eventsessions_title` varchar(50) NOT NULL,
  `eventsessions_limitnum` int(11) DEFAULT NULL,
  `eventsessions_start_date` datetime NOT NULL,
  `eventsessions_end_date` datetime NOT NULL,
  `eventsessions_isCanceled` int(11) NOT NULL,
  `eventsessions_modified` datetime NOT NULL,
  `eventsessions_created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `eventsessions`
--

INSERT INTO `eventsessions` (`eventsessions_id`, `event_id`, `eventsessions_title`, `eventsessions_limitnum`, `eventsessions_start_date`, `eventsessions_end_date`, `eventsessions_isCanceled`, `eventsessions_modified`, `eventsessions_created`) VALUES
(1, 1, '場次一', 80, '2022-12-20 10:00:00', '2022-12-20 12:00:00', 0, '2022-12-01 12:13:00', '2022-12-01 12:13:00'),
(2, 2, '場次一', 0, '2022-09-27 10:00:00', '2022-10-29 17:00:00', 0, '2022-09-20 10:39:00', '2022-09-20 10:39:00'),
(3, 3, '場次一', 2000, '2022-12-16 19:30:00', '2022-12-16 21:00:00', 0, '2022-11-01 11:45:00', '2022-11-01 11:45:00'),
(4, 4, '瑞典—花巷裡的記憶Garden Lane（輔12）', 80, '2022-12-08 18:00:00', '2022-12-08 20:00:00', 0, '2022-11-18 09:36:00', '2022-11-18 09:36:00'),
(5, 4, '德國—離散的姊妹Sister Apart（護）', 80, '2022-12-13 10:10:00', '2022-12-14 12:00:00', 1, '2022-12-01 10:51:00', '2022-11-18 09:36:00'),
(6, 4, '西班牙—校園姊妹淘School Girls（護）', 80, '2022-12-14 18:00:00', '2022-12-14 20:00:00', 0, '2022-11-18 09:36:00', '2022-11-18 09:36:00'),
(7, 4, '義大利—活著，不難Easy Living（輔12）', 80, '2022-12-15 18:00:00', '2022-12-15 20:00:00', 0, '2022-11-18 09:36:00', '2022-11-18 09:36:00'),
(8, 4, '英國—自由之歌短片集More Films for Freedom 2022（護6）', 80, '2022-12-15 20:10:00', '2022-12-15 22:00:00', 0, '2022-11-18 09:36:00', '2022-11-18 09:36:00'),
(9, 5, '場次一', 10, '2022-11-14 15:00:00', '2022-11-14 18:00:00', 0, '2022-11-01 14:19:00', '2022-11-01 14:19:00'),
(10, 5, '場次二', 10, '2022-11-26 10:00:00', '2022-11-26 17:00:00', 0, '2022-11-01 14:19:00', '2022-11-01 14:19:00'),
(11, 6, '場次一', 60, '2022-11-19 14:00:00', '2022-11-19 17:00:00', 0, '2022-11-01 15:31:00', '2022-11-01 15:31:00'),
(12, 7, '場次一', 80, '2021-12-17 14:00:00', '2021-12-17 17:00:00', 1, '2021-12-14 08:56:00', '2021-12-01 15:12:00');

-- --------------------------------------------------------

--
-- 資料表結構 `eventtype`
--

CREATE TABLE `eventtype` (
  `eventtype_id` int(20) NOT NULL,
  `eventtype_description` char(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `eventtype`
--

INSERT INTO `eventtype` (`eventtype_id`, `eventtype_description`) VALUES
(1, '演講座談'),
(2, '藝文特展'),
(3, '音樂舞台'),
(4, '107影享會'),
(5, '工作坊'),
(6, '崑曲展演'),
(7, '其他');

-- --------------------------------------------------------

--
-- 資料表結構 `member`
--

CREATE TABLE `member` (
  `member_id` int(50) NOT NULL,
  `member_name` varchar(30) NOT NULL,
  `member_email` varchar(100) NOT NULL,
  `member_usermodified` datetime NOT NULL,
  `member_administratormodified` datetime NOT NULL,
  `member_created` datetime NOT NULL,
  `member_isDeleted` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `member`
--

INSERT INTO `member` (`member_id`, `member_name`, `member_email`, `member_usermodified`, `member_administratormodified`, `member_created`, `member_isDeleted`) VALUES
(1, '王曉明', '100100100100@cc.ncu.edu.tw', '2020-05-29 10:32:00', '2020-03-23 00:34:00', '2020-03-23 00:34:00', 0),
(2, '李小美', 'abc1123@gmail.com', '2020-06-12 16:47:00', '2022-01-03 09:10:00', '2020-06-10 18:01:00', 0),
(3, '陳志明', '1234567890@g.ncu.edu.tw', '2020-12-07 18:59:00', '2021-04-12 10:31:00', '2020-11-09 21:38:00', 0),
(4, '周傑侖', 'asdfghjkl987@yahoo.com.tw', '2021-01-14 01:04:00', '2020-12-20 23:07:00', '2020-12-20 23:07:00', 0),
(5, '林峻杰', 'xyz567@gmail.com', '2022-03-05 02:25:00', '2022-09-01 14:04:00', '2021-02-25 16:52:00', 1),
(6, '陳信鴻', '150403835@g.ncu.edu.tw', '2021-07-06 20:33:00', '2022-10-27 11:20:00', '2021-04-28 20:44:00', 0),
(7, '林一', 'one.001@gmail.com', '2021-09-06 12:01:00', '2021-09-06 12:01:00', '2021-09-06 12:01:00', 0),
(8, '王大同', '111222333@g.ncu.edu.tw', '2021-10-27 03:25:00', '2021-10-27 03:25:00', '2021-10-27 03:25:00', 0),
(9, '張三', '333333333@cc.ncu.edu.tw', '2022-08-13 22:12:00', '2022-05-11 04:19:00', '2022-05-11 04:19:00', 0),
(10, '李四', 'qwertyuiop@yahoo.com.tw', '2022-06-18 06:03:00', '2022-07-01 13:24:00', '2022-06-18 06:03:00', 1);

-- --------------------------------------------------------

--
-- 資料表結構 `membercredential`
--

CREATE TABLE `membercredential` (
  `membercredential_id` int(50) NOT NULL,
  `member_id` int(50) NOT NULL,
  `member_hashedpassword` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `membercredential`
--

INSERT INTO `membercredential` (`membercredential_id`, `member_id`, `member_hashedpassword`) VALUES
(1, 1, '$2a$12$AJVJW3mM/PeGx9j3gopsweWwEkUCioFVpfCRhFDXyZykDAXEMsoMm'),
(2, 2, '$2a$12$CQRkvlNQXEYp4LM6/EH.oe5PymMypQqCYk8uYMfw.fH9Q2K8JHs1q'),
(3, 3, '$2a$12$JBQtnYu/5ygkFWOodeFJyek87BfOyL.8zSSvwU/Pdca/WT/PQR6mC'),
(4, 4, '$2a$12$KpXuUY/AqG8pNsGpmMWro.aJwPxPs66Ng7uKNWxuVH3MiVE03IsnO'),
(5, 5, '$2a$12$EvRpO3pd59Dm88y4oXh/LO44YBKgNGikjEJlBxs0xNLF1QoZwbpL2'),
(6, 6, '$2a$12$wTdwmKkbd6E0SPvpJrjBb.KfgvehlNjT2Zhb/rnhpqQQhNkDlqTTS'),
(7, 7, '$2a$12$ZMVWBbjZPZ3J1Dc0G/7Qou9OvL3o0IeJs9vgSCa/bIIv.fLQa9VUa'),
(8, 8, '$2a$12$y6.fcCi5YhZYB.genrOMMOdwdo41V7lhNWr1KDmsy3eZmhS.44DZq'),
(9, 9, '$2a$12$buphSGv4AKbyCaA.qui8dufhfm4VKewzn/ADDIUZsEqM3LX/MSF9G'),
(10, 10, '$2a$12$2SC7zpr4lxdNaBwXRMW/zOBabS3BkzMxLLIJRWwRIs8Qv2SteLwVq');

-- --------------------------------------------------------

--
-- 資料表結構 `sessionmemberdetail`
--

CREATE TABLE `sessionmemberdetail` (
  `sessionmemberdetail_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `eventsessions_id` int(11) NOT NULL,
  `sessionmemberdetail_modified` datetime NOT NULL,
  `sessionmemberdetail_created` datetime NOT NULL,
  `applystatus_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `sessionmemberdetail`
--

INSERT INTO `sessionmemberdetail` (`sessionmemberdetail_id`, `member_id`, `eventsessions_id`, `sessionmemberdetail_modified`, `sessionmemberdetail_created`, `applystatus_id`) VALUES
(1, 1, 4, '2022-11-22 11:11:00', '2022-11-20 00:12:00', 1),
(2, 1, 10, '2022-11-02 01:04:00', '2022-11-02 01:04:00', 1),
(3, 2, 10, '2022-11-02 02:10:00', '2022-11-02 02:10:00', 1),
(4, 3, 10, '2022-11-03 08:45:00', '2022-11-03 08:45:00', 1),
(5, 4, 4, '2022-11-22 07:36:00', '2022-11-21 19:45:00', 3),
(6, 4, 3, '2022-11-22 21:36:00', '2022-11-22 21:36:00', 1),
(7, 4, 10, '2022-11-03 11:53:00', '2022-11-03 11:53:00', 1),
(8, 5, 10, '2022-11-03 20:25:00', '2022-11-03 20:25:00', 1),
(9, 6, 10, '2022-11-03 23:00:00', '2022-11-03 23:00:00', 1),
(10, 7, 10, '2022-11-03 23:57:00', '2022-11-03 23:57:00', 1),
(11, 7, 8, '2022-11-20 09:36:00', '2022-11-20 09:36:00', 1),
(12, 7, 3, '2022-11-06 13:45:00', '2022-11-06 13:45:00', 2),
(13, 8, 10, '2022-11-04 09:33:00', '2022-11-04 00:03:00', 2),
(14, 9, 10, '2022-11-04 09:41:00', '2022-11-04 09:41:00', 1),
(15, 9, 3, '2022-11-10 01:04:00', '2022-11-10 01:04:00', 3),
(16, 9, 7, '2022-11-21 11:29:00', '2022-11-21 11:29:00', 1),
(17, 10, 10, '2022-11-04 05:22:00', '2022-11-04 05:22:00', 1),
(18, 10, 3, '2022-11-11 11:04:00', '2022-11-11 11:04:00', 1),
(19, 10, 6, '2022-11-25 17:25:00', '2022-11-25 17:25:00', 1),
(20, 8, 10, '2022-11-04 15:43:00', '2022-11-04 15:43:00', 1),
(21, 6, 7, '2022-11-25 23:26:00', '2022-11-23 21:36:00', 3),
(22, 5, 8, '2022-11-28 01:16:00', '2022-11-24 11:25:00', 3),
(23, 3, 8, '2022-11-29 21:57:00', '2022-11-25 10:07:00', 2),
(24, 2, 4, '2022-11-29 23:53:00', '2022-11-26 22:45:00', 2);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`administrator_id`);

--
-- 資料表索引 `administratorcredential`
--
ALTER TABLE `administratorcredential`
  ADD PRIMARY KEY (`administratorcredential_id`),
  ADD KEY `administrator_id_administratorcredential` (`administrator_id`);

--
-- 資料表索引 `applystatus`
--
ALTER TABLE `applystatus`
  ADD PRIMARY KEY (`applystatus_id`);

--
-- 資料表索引 `collection`
--
ALTER TABLE `collection`
  ADD PRIMARY KEY (`collection_id`),
  ADD KEY `member_id_collection` (`member_id`),
  ADD KEY `event_id_collection` (`event_id`);

--
-- 資料表索引 `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `eventtype_id_event` (`eventtype_id`);

--
-- 資料表索引 `eventsessions`
--
ALTER TABLE `eventsessions`
  ADD PRIMARY KEY (`eventsessions_id`),
  ADD KEY `event_id_eventsessions` (`event_id`);

--
-- 資料表索引 `eventtype`
--
ALTER TABLE `eventtype`
  ADD PRIMARY KEY (`eventtype_id`);

--
-- 資料表索引 `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`member_id`);

--
-- 資料表索引 `membercredential`
--
ALTER TABLE `membercredential`
  ADD PRIMARY KEY (`membercredential_id`),
  ADD KEY `member_id` (`member_id`);

--
-- 資料表索引 `sessionmemberdetail`
--
ALTER TABLE `sessionmemberdetail`
  ADD PRIMARY KEY (`sessionmemberdetail_id`),
  ADD KEY `member_id_sessionmemberdetail` (`member_id`),
  ADD KEY `eventsessions_id_sessionmemberdetail` (`eventsessions_id`),
  ADD KEY `applystatus_id_sessionmemberdetail` (`applystatus_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `administrator`
--
ALTER TABLE `administrator`
  MODIFY `administrator_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `administratorcredential`
--
ALTER TABLE `administratorcredential`
  MODIFY `administratorcredential_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `collection`
--
ALTER TABLE `collection`
  MODIFY `collection_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `event`
--
ALTER TABLE `event`
  MODIFY `event_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `eventsessions`
--
ALTER TABLE `eventsessions`
  MODIFY `eventsessions_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `eventtype`
--
ALTER TABLE `eventtype`
  MODIFY `eventtype_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `member`
--
ALTER TABLE `member`
  MODIFY `member_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `membercredential`
--
ALTER TABLE `membercredential`
  MODIFY `membercredential_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `sessionmemberdetail`
--
ALTER TABLE `sessionmemberdetail`
  MODIFY `sessionmemberdetail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `administratorcredential`
--
ALTER TABLE `administratorcredential`
  ADD CONSTRAINT `administrator_id_administratorcredential` FOREIGN KEY (`administrator_id`) REFERENCES `administrator` (`administrator_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `collection`
--
ALTER TABLE `collection`
  ADD CONSTRAINT `event_id_collection` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `member_id_collection` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `eventtype_id_event` FOREIGN KEY (`eventtype_id`) REFERENCES `eventtype` (`eventtype_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `eventsessions`
--
ALTER TABLE `eventsessions`
  ADD CONSTRAINT `event_id_eventsessions` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `membercredential`
--
ALTER TABLE `membercredential`
  ADD CONSTRAINT `member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `sessionmemberdetail`
--
ALTER TABLE `sessionmemberdetail`
  ADD CONSTRAINT `applystatus_id_sessionmemberdetail` FOREIGN KEY (`applystatus_id`) REFERENCES `applystatus` (`applystatus_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `eventsessions_id_sessionmemberdetail` FOREIGN KEY (`eventsessions_id`) REFERENCES `eventsessions` (`eventsessions_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `member_id_sessionmemberdetail` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
