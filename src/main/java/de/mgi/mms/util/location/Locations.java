package de.mgi.mms.util.location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * This class is able to translate location codes out of ISO 3166 into there
 * different representations.
 * 
 * These representations are:
 * 
 * The country real name | ISO ALAPA 2 code | ISO ALPHA 3 code | ISO numerical
 * code
 * 
 * e.g.: Afghanistan |AF | AFG | 004
 * 
 * @author Johannes.Hoehne
 * 
 */
public class Locations {

	// all locations in the world
	private static Set<Location> locations = new HashSet<Locations.Location>();

	private static Map<String, Location> byName = new HashMap<String, Locations.Location>();

	private static Map<String, Location> byIsoAlpha2Code = new HashMap<String, Locations.Location>();

	private static Map<String, Location> byIsoAlpha3Code = new HashMap<String, Locations.Location>();

	private static Map<String, Location> byIsoNumericalCode = new HashMap<String, Locations.Location>();

	public static Location getByName(String code) {
		checkInitialisation();

		return byName.get(code.toUpperCase());
	}

	public static Location getByIsoAlpha2Code(String code) {
		checkInitialisation();

		return byIsoAlpha2Code.get(code.toUpperCase());
	}

	public static Location getByIsoAlpha3Code(String code) {
		checkInitialisation();

		return byIsoAlpha3Code.get(code.toUpperCase());
	}

	public static Location getByIsoNumericalCode(String code) {
		checkInitialisation();

		return byIsoNumericalCode.get(code);
	}

	public static class Location {
		private String CountryName, IsoAlpha2Code, IsoAlpha3Code, IsoNumericalCode;

		private Location(String countryName, String isoAlpha2Code, String isoAlpha3Code, String isoNumericalCode) {
			if (countryName == null || countryName.trim().equals("")) {
				throw new IllegalArgumentException("countryName must not be null or empty");
			}

			if (isoAlpha2Code == null || isoAlpha2Code.length() != 2) {
				throw new IllegalArgumentException("isoAlpha2Code must not be null or different from two letters");
			}

			if (isoAlpha3Code == null || isoAlpha3Code.length() != 3) {
				throw new IllegalArgumentException("isoAlpha3Code must not be null or different from three letters");
			}

			int asInt = Integer.parseInt(isoNumericalCode.trim());
			if (isoNumericalCode == null || isoNumericalCode.length() != 3 || asInt < 0 || asInt >= 1000) {
				throw new IllegalArgumentException("isoNumericalCode must not be null or different from three digits");
			}

			CountryName = countryName;
			IsoAlpha2Code = isoAlpha2Code.toUpperCase();
			IsoAlpha3Code = isoAlpha3Code.toUpperCase();
			IsoNumericalCode = isoNumericalCode.trim();
		}

		public final String getCountryName() {
			return CountryName;
		}

		public final String getIsoAlpha2Code() {
			return IsoAlpha2Code;
		}

		public final String getIsoAlpha3Code() {
			return IsoAlpha3Code;
		}

		public final String getIsoNumericalCode() {
			return IsoNumericalCode;
		}

		public Locale getJavaLocale() {
			for (Locale locale : Locale.getAvailableLocales()) {
				if (locale.getISO3Country().equals(this.IsoAlpha3Code)) {
					return locale;
				}
			}
			return null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((CountryName == null) ? 0 : CountryName.hashCode());
			result = prime * result + ((IsoAlpha2Code == null) ? 0 : IsoAlpha2Code.hashCode());
			result = prime * result + ((IsoAlpha3Code == null) ? 0 : IsoAlpha3Code.hashCode());
			result = prime * result + ((IsoNumericalCode == null) ? 0 : IsoNumericalCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Location other = (Location) obj;
			if (CountryName == null) {
				if (other.CountryName != null)
					return false;
			} else if (!CountryName.equals(other.CountryName))
				return false;
			if (IsoAlpha2Code == null) {
				if (other.IsoAlpha2Code != null)
					return false;
			} else if (!IsoAlpha2Code.equals(other.IsoAlpha2Code))
				return false;
			if (IsoAlpha3Code == null) {
				if (other.IsoAlpha3Code != null)
					return false;
			} else if (!IsoAlpha3Code.equals(other.IsoAlpha3Code))
				return false;
			if (IsoNumericalCode == null) {
				if (other.IsoNumericalCode != null)
					return false;
			} else if (!IsoNumericalCode.equals(other.IsoNumericalCode))
				return false;
			return true;
		}

		/**
		 * Constructs a <code>String</code> with all attributes in name = value
		 * format.
		 * 
		 * @return a <code>String</code> representation of this object.
		 */
		public String toString() {
			final String TAB = "    ";

			String retValue = "";

			retValue = "Location ( " //
					+ "CountryName = " + this.CountryName + TAB //
					+ "IsoAlpha2Code = " + this.IsoAlpha2Code + TAB //
					+ "IsoAlpha3Code = " + this.IsoAlpha3Code + TAB //
					+ "IsoNumericalCode = " + this.IsoNumericalCode + TAB //
					+ " )";

			return retValue;
		}

	}

	/**
	 * we do it just once in the life time of the VM
	 */
	private static synchronized void checkInitialisation() {
		if (locations.isEmpty()) {
			initLocations();
			initMaps();
		}
	}

	/**
	 * for the performance of querying locations we put it into maps
	 */
	private static void initMaps() {
		for (Location location : locations) {
			byName.put(location.getCountryName(), location);
			byIsoAlpha2Code.put(location.getIsoAlpha2Code(), location);
			byIsoAlpha3Code.put(location.getIsoAlpha3Code(), location);
			byIsoNumericalCode.put(location.getIsoNumericalCode(), location);
		}
	}

	private static void initLocations() {
		// downloaded from http://www.ip2location.com/download/iso3166.txt
		locations.add(new Location("AFGHANISTAN", "AF", "AFG", "004"));
		locations.add(new Location("ÅLAND ISLANDS", "AX", "ALA", "248"));
		locations.add(new Location("ALBANIA", "AL", "ALB", "008"));
		locations.add(new Location("ALGERIA", "DZ", "DZA", "012"));
		locations.add(new Location("AMERICAN SAMOA", "AS", "ASM", "016"));
		locations.add(new Location("ANDORRA", "AD", "AND", "020"));
		locations.add(new Location("ANGOLA", "AO", "AGO", "024"));
		locations.add(new Location("ANGUILLA", "AI", "AIA", "660"));
		locations.add(new Location("ANTARCTICA", "AQ", "ATA", "010"));
		locations.add(new Location("ANTIGUA AND BARBUDA", "AG", "ATG", "028"));
		locations.add(new Location("ARGENTINA", "AR", "ARG", "032"));
		locations.add(new Location("ARMENIA", "AM", "ARM", "051"));
		locations.add(new Location("ARUBA", "AW", "ABW", "533"));
		locations.add(new Location("AUSTRALIA", "AU", "AUS", "036"));
		locations.add(new Location("AUSTRIA", "AT", "AUT", "040"));
		locations.add(new Location("AZERBAIJAN", "AZ", "AZE", "031"));
		locations.add(new Location("BAHAMAS", "BS", "BHS", "044"));
		locations.add(new Location("BAHRAIN", "BH", "BHR", "048"));
		locations.add(new Location("BANGLADESH", "BD", "BGD", "050"));
		locations.add(new Location("BARBADOS", "BB", "BRB", "052"));
		locations.add(new Location("BELARUS", "BY", "BLR", "112"));
		locations.add(new Location("BELGIUM", "BE", "BEL", "056"));
		locations.add(new Location("BELIZE", "BZ", "BLZ", "084"));
		locations.add(new Location("BENIN", "BJ", "BEN", "204"));
		locations.add(new Location("BERMUDA", "BM", "BMU", "060"));
		locations.add(new Location("BHUTAN", "BT", "BTN", "064"));
		locations.add(new Location("BOLIVIA", "BO", "BOL", "068"));
		locations.add(new Location("BOSNIA AND HERZEGOVINA", "BA", "BIH", "070"));
		locations.add(new Location("BOTSWANA", "BW", "BWA", "072"));
		locations.add(new Location("BOUVET ISLAND", "BV", "BVT", "074"));
		locations.add(new Location("BRAZIL", "BR", "BRA", "076"));
		locations.add(new Location("BRITISH INDIAN OCEAN TERRITORY", "IO", "IOT", "086"));
		locations.add(new Location("BRUNEI DARUSSALAM", "BN", "BRN", "096"));
		locations.add(new Location("BULGARIA", "BG", "BGR", "100"));
		locations.add(new Location("BURKINA FASO", "BF", "BFA", "854"));
		locations.add(new Location("BURUNDI", "BI", "BDI", "108"));
		locations.add(new Location("CAMBODIA", "KH", "KHM", "116"));
		locations.add(new Location("CAMEROON", "CM", "CMR", "120"));
		locations.add(new Location("CANADA", "CA", "CAN", "124"));
		locations.add(new Location("CAPE VERDE", "CV", "CPV", "132"));
		locations.add(new Location("CAYMAN ISLANDS", "KY", "CYM", "136"));
		locations.add(new Location("CENTRAL AFRICAN REPUBLIC", "CF", "CAF", "140"));
		locations.add(new Location("CHAD", "TD", "TCD", "148"));
		locations.add(new Location("CHILE", "CL", "CHL", "152"));
		locations.add(new Location("CHINA", "CN", "CHN", "156"));
		locations.add(new Location("CHRISTMAS ISLAND", "CX", "CXR", "162"));
		locations.add(new Location("COCOS (KEELING) ISLANDS", "CC", "CCK", "166"));
		locations.add(new Location("COLOMBIA", "CO", "COL", "170"));
		locations.add(new Location("COMOROS", "KM", "COM", "174"));
		locations.add(new Location("CONGO", "CG", "COG", "178"));
		locations.add(new Location("CONGO, THE DEMOCRATIC REPUBLIC OF THE", "CD", "COD", "180"));
		locations.add(new Location("COOK ISLANDS", "CK", "COK", "184"));
		locations.add(new Location("COSTA RICA", "CR", "CRI", "188"));
		locations.add(new Location("COTE D'IVOIRE", "CI", "CIV", "384"));
		locations.add(new Location("CROATIA", "HR", "HRV", "191"));
		locations.add(new Location("CUBA", "CU", "CUB", "192"));
		locations.add(new Location("CYPRUS", "CY", "CYP", "196"));
		locations.add(new Location("CZECH REPUBLIC", "CZ", "CZE", "203"));
		locations.add(new Location("DENMARK", "DK", "DNK", "208"));
		locations.add(new Location("DJIBOUTI", "DJ", "DJI", "262"));
		locations.add(new Location("DOMINICA", "DM", "DMA", "212"));
		locations.add(new Location("DOMINICAN REPUBLIC", "DO", "DOM", "214"));
		locations.add(new Location("ECUADOR", "EC", "ECU", "218"));
		locations.add(new Location("EGYPT", "EG", "EGY", "818"));
		locations.add(new Location("EL SALVADOR", "SV", "SLV", "222"));
		locations.add(new Location("EQUATORIAL GUINEA", "GQ", "GNQ", "226"));
		locations.add(new Location("ERITREA", "ER", "ERI", "232"));
		locations.add(new Location("ESTONIA", "EE", "EST", "233"));
		locations.add(new Location("ETHIOPIA", "ET", "ETH", "231"));
		locations.add(new Location("EUROPEAN UNION", "EU", "EUN", "097"));
		locations.add(new Location("FALKLAND ISLANDS (MALVINAS)", "FK", "FLK", "238"));
		locations.add(new Location("FAROE ISLANDS", "FO", "FRO", "234"));
		locations.add(new Location("FIJI", "FJ", "FJI", "242"));
		locations.add(new Location("FINLAND", "FI", "FIN", "246"));
		locations.add(new Location("FRANCE", "FR", "FRA", "250"));
		locations.add(new Location("FRENCH GUIANA", "GF", "GUF", "254"));
		locations.add(new Location("FRENCH POLYNESIA", "PF", "PYF", "258"));
		locations.add(new Location("FRENCH SOUTHERN TERRITORIES", "TF", "ATF", "260"));
		locations.add(new Location("GABON", "GA", "GAB", "266"));
		locations.add(new Location("GAMBIA", "GM", "GMB", "270"));
		locations.add(new Location("GEORGIA", "GE", "GEO", "268"));
		locations.add(new Location("GERMANY", "DE", "DEU", "276"));
		locations.add(new Location("GHANA", "GH", "GHA", "288"));
		locations.add(new Location("GIBRALTAR", "GI", "GIB", "292"));
		locations.add(new Location("GREECE", "GR", "GRC", "300"));
		locations.add(new Location("GREENLAND", "GL", "GRL", "304"));
		locations.add(new Location("GRENADA", "GD", "GRD", "308"));
		locations.add(new Location("GUADELOUPE", "GP", "GLP", "312"));
		locations.add(new Location("GUAM", "GU", "GUM", "316"));
		locations.add(new Location("GUATEMALA", "GT", "GTM", "320"));
		locations.add(new Location("GUINEA", "GN", "GIN", "324"));
		locations.add(new Location("GUINEA-BISSAU", "GW", "GNB", "624"));
		locations.add(new Location("GUYANA", "GY", "GUY", "328"));
		locations.add(new Location("HAITI", "HT", "HTI", "332"));
		locations.add(new Location("HEARD ISLAND AND MCDONALD ISLANDS", "HM", "HMD", "334"));
		locations.add(new Location("HOLY SEE (VATICAN CITY STATE)", "VA", "VAT", "336"));
		locations.add(new Location("HONDURAS", "HN", "HND", "340"));
		locations.add(new Location("HONG KONG", "HK", "HKG", "344"));
		locations.add(new Location("HUNGARY", "HU", "HUN", "348"));
		locations.add(new Location("ICELAND", "IS", "ISL", "352"));
		locations.add(new Location("INDIA", "IN", "IND", "356"));
		locations.add(new Location("INDONESIA", "ID", "IDN", "360"));
		locations.add(new Location("IRAN, ISLAMIC REPUBLIC OF", "IR", "IRN", "364"));
		locations.add(new Location("IRAQ", "IQ", "IRQ", "368"));
		locations.add(new Location("IRELAND", "IE", "IRL", "372"));
		locations.add(new Location("ISRAEL", "IL", "ISR", "376"));
		locations.add(new Location("ITALY", "IT", "ITA", "380"));
		locations.add(new Location("JAMAICA", "JM", "JAM", "388"));
		locations.add(new Location("JAPAN", "JP", "JPN", "392"));
		locations.add(new Location("JORDAN", "JO", "JOR", "400"));
		locations.add(new Location("KAZAKHSTAN", "KZ", "KAZ", "398"));
		locations.add(new Location("KENYA", "KE", "KEN", "404"));
		locations.add(new Location("KIRIBATI", "KI", "KIR", "296"));
		locations.add(new Location("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF", "KP", "PRK", "408"));
		locations.add(new Location("KOREA, REPUBLIC OF", "KR", "KOR", "410"));
		locations.add(new Location("KUWAIT", "KW", "KWT", "414"));
		locations.add(new Location("KYRGYZSTAN", "KG", "KGZ", "417"));
		locations.add(new Location("LAO PEOPLE'S DEMOCRATIC REPUBLIC", "LA", "LAO", "418"));
		locations.add(new Location("LATVIA", "LV", "LVA", "428"));
		locations.add(new Location("LEBANON", "LB", "LBN", "422"));
		locations.add(new Location("LESOTHO", "LS", "LSO", "426"));
		locations.add(new Location("LIBERIA", "LR", "LBR", "430"));
		locations.add(new Location("LIBYAN ARAB JAMAHIRIYA", "LY", "LBY", "434"));
		locations.add(new Location("LIECHTENSTEIN", "LI", "LIE", "438"));
		locations.add(new Location("LITHUANIA", "LT", "LTU", "440"));
		locations.add(new Location("LUXEMBOURG", "LU", "LUX", "442"));
		locations.add(new Location("MACAO", "MO", "MAC", "446"));
		locations.add(new Location("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF", "MK", "MKD", "807"));
		locations.add(new Location("MADAGASCAR", "MG", "MDG", "450"));
		locations.add(new Location("MALAWI", "MW", "MWI", "454"));
		locations.add(new Location("MALAYSIA", "MY", "MYS", "458"));
		locations.add(new Location("MALDIVES", "MV", "MDV", "462"));
		locations.add(new Location("MALI", "ML", "MLI", "466"));
		locations.add(new Location("MALTA", "MT", "MLT", "470"));
		locations.add(new Location("MARSHALL ISLANDS", "MH", "MHL", "584"));
		locations.add(new Location("MARTINIQUE", "MQ", "MTQ", "474"));
		locations.add(new Location("MAURITANIA", "MR", "MRT", "478"));
		locations.add(new Location("MAURITIUS", "MU", "MUS", "480"));
		locations.add(new Location("MAYOTTE", "YT", "MYT", "175"));
		locations.add(new Location("MEXICO", "MX", "MEX", "484"));
		locations.add(new Location("MICRONESIA, FEDERATED STATES OF", "FM", "FSM", "583"));
		locations.add(new Location("MOLDOVA, REPUBLIC OF", "MD", "MDA", "498"));
		locations.add(new Location("MONACO", "MC", "MCO", "492"));
		locations.add(new Location("MONGOLIA", "MN", "MNG", "496"));
		locations.add(new Location("MONTSERRAT", "MS", "MSR", "500"));
		locations.add(new Location("MOROCCO", "MA", "MAR", "504"));
		locations.add(new Location("MOZAMBIQUE", "MZ", "MOZ", "508"));
		locations.add(new Location("MYANMAR", "MM", "MMR", "104"));
		locations.add(new Location("NAMIBIA", "NA", "NAM", "516"));
		locations.add(new Location("NAURU", "NR", "NRU", "520"));
		locations.add(new Location("NEPAL", "NP", "NPL", "524"));
		locations.add(new Location("NETHERLANDS", "NL", "NLD", "528"));
		locations.add(new Location("NETHERLANDS ANTILLES", "AN", "ANT", "530"));
		locations.add(new Location("NEW CALEDONIA", "NC", "NCL", "540"));
		locations.add(new Location("NEW ZEALAND", "NZ", "NZL", "554"));
		locations.add(new Location("NICARAGUA", "NI", "NIC", "558"));
		locations.add(new Location("NIGER", "NE", "NER", "562"));
		locations.add(new Location("NIGERIA", "NG", "NGA", "566"));
		locations.add(new Location("NIUE", "NU", "NIU", "570"));
		locations.add(new Location("NORFOLK ISLAND", "NF", "NFK", "574"));
		locations.add(new Location("NORTHERN MARIANA ISLANDS", "MP", "MNP", "580"));
		locations.add(new Location("NORWAY", "NO", "NOR", "578"));
		locations.add(new Location("OMAN", "OM", "OMN", "512"));
		locations.add(new Location("PAKISTAN", "PK", "PAK", "586"));
		locations.add(new Location("PALAU", "PW", "PLW", "585"));
		locations.add(new Location("PALESTINIAN TERRITORY, OCCUPIED", "PS", "PSE", "275"));
		locations.add(new Location("PANAMA", "PA", "PAN", "591"));
		locations.add(new Location("PAPUA NEW GUINEA", "PG", "PNG", "598"));
		locations.add(new Location("PARAGUAY", "PY", "PRY", "600"));
		locations.add(new Location("PERU", "PE", "PER", "604"));
		locations.add(new Location("PHILIPPINES", "PH", "PHL", "608"));
		locations.add(new Location("PITCAIRN", "PN", "PCN", "612"));
		locations.add(new Location("POLAND", "PL", "POL", "616"));
		locations.add(new Location("PORTUGAL", "PT", "PRT", "620"));
		locations.add(new Location("PUERTO RICO", "PR", "PRI", "630"));
		locations.add(new Location("QATAR", "QA", "QAT", "634"));
		locations.add(new Location("REUNION", "RE", "REU", "638"));
		locations.add(new Location("ROMANIA", "RO", "ROU", "642"));
		locations.add(new Location("RUSSIAN FEDERATION", "RU", "RUS", "643"));
		locations.add(new Location("RWANDA", "RW", "RWA", "646"));
		locations.add(new Location("SAINT HELENA", "SH", "SHN", "654"));
		locations.add(new Location("SAINT KITTS AND NEVIS", "KN", "KNA", "659"));
		locations.add(new Location("SAINT LUCIA", "LC", "LCA", "662"));
		locations.add(new Location("SAINT PIERRE AND MIQUELON", "PM", "SPM", "666"));
		locations.add(new Location("SAINT VINCENT AND THE GRENADINES", "VC", "VCT", "670"));
		locations.add(new Location("SAMOA", "WS", "WSM", "882"));
		locations.add(new Location("SAN MARINO", "SM", "SMR", "674"));
		locations.add(new Location("SAO TOME AND PRINCIPE", "ST", "STP", "678"));
		locations.add(new Location("SAUDI ARABIA", "SA", "SAU", "682"));
		locations.add(new Location("SENEGAL", "SN", "SEN", "686"));
		locations.add(new Location("SERBIA AND MONTENEGRO", "CS", "SCG", "891"));
		locations.add(new Location("SEYCHELLES", "SC", "SYC", "690"));
		locations.add(new Location("SIERRA LEONE", "SL", "SLE", "694"));
		locations.add(new Location("SINGAPORE", "SG", "SGP", "702"));
		locations.add(new Location("SLOVAKIA", "SK", "SVK", "703"));
		locations.add(new Location("SLOVENIA", "SI", "SVN", "705"));
		locations.add(new Location("SOLOMON ISLANDS", "SB", "SLB", "090"));
		locations.add(new Location("SOMALIA", "SO", "SOM", "706"));
		locations.add(new Location("SOUTH AFRICA", "ZA", "ZAF", "710"));
		locations.add(new Location("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS", "GS", "SGS", "239"));
		locations.add(new Location("SPAIN", "ES", "ESP", "724"));
		locations.add(new Location("SRI LANKA", "LK", "LKA", "144"));
		locations.add(new Location("SUDAN", "SD", "SDN", "736"));
		locations.add(new Location("SURINAME", "SR", "SUR", "740"));
		locations.add(new Location("SVALBARD AND JAN MAYEN", "SJ", "SJM", "744"));
		locations.add(new Location("SWAZILAND", "SZ", "SWZ", "748"));
		locations.add(new Location("SWEDEN", "SE", "SWE", "752"));
		locations.add(new Location("SWITZERLAND", "CH", "CHE", "756"));
		locations.add(new Location("SYRIAN ARAB REPUBLIC", "SY", "SYR", "760"));
		locations.add(new Location("TAIWAN", "TW", "TWN", "158"));
		locations.add(new Location("TAJIKISTAN", "TJ", "TJK", "762"));
		locations.add(new Location("TANZANIA, UNITED REPUBLIC OF", "TZ", "TZA", "834"));
		locations.add(new Location("THAILAND", "TH", "THA", "764"));
		locations.add(new Location("TIMOR-LESTE", "TL", "TLS", "626"));
		locations.add(new Location("TOGO", "TG", "TGO", "768"));
		locations.add(new Location("TOKELAU", "TK", "TKL", "772"));
		locations.add(new Location("TONGA", "TO", "TON", "776"));
		locations.add(new Location("TRINIDAD AND TOBAGO", "TT", "TTO", "780"));
		locations.add(new Location("TUNISIA", "TN", "TUN", "788"));
		locations.add(new Location("TURKEY", "TR", "TUR", "792"));
		locations.add(new Location("TURKMENISTAN", "TM", "TKM", "795"));
		locations.add(new Location("TURKS AND CAICOS ISLANDS", "TC", "TCA", "796"));
		locations.add(new Location("TUVALU", "TV", "TUV", "798"));
		locations.add(new Location("UGANDA", "UG", "UGA", "800"));
		locations.add(new Location("UKRAINE", "UA", "UKR", "804"));
		locations.add(new Location("UNITED ARAB EMIRATES", "AE", "ARE", "784"));
		locations.add(new Location("UNITED KINGDOM", "GB", "GBR", "826"));
		locations.add(new Location("UNITED KINGDOM", "UK", "GBR", "826"));
		locations.add(new Location("UNITED STATES", "US", "USA", "840"));
		locations.add(new Location("UNITED STATES MINOR OUTLYING ISLANDS", "UM", "UMI", "581"));
		locations.add(new Location("URUGUAY", "UY", "URY", "858"));
		locations.add(new Location("UZBEKISTAN", "UZ", "UZB", "860"));
		locations.add(new Location("VANUATU", "VU", "VUT", "548"));
		locations.add(new Location("VENEZUELA", "VE", "VEN", "862"));
		locations.add(new Location("VIET NAM", "VN", "VNM", "704"));
		locations.add(new Location("VIRGIN ISLANDS, BRITISH", "VG", "VGB", "092"));
		locations.add(new Location("VIRGIN ISLANDS, U.S.", "VI", "VIR", "850"));
		locations.add(new Location("WALLIS AND FUTUNA", "WF", "WLF", "876"));
		locations.add(new Location("WESTERN SAHARA", "EH", "ESH", "732"));
		locations.add(new Location("YEMEN", "YE", "YEM", "887"));
		locations.add(new Location("SERBIA AND MONTENEGRO", "YU", "SCG", "891"));
		locations.add(new Location("ZAMBIA", "ZM", "ZMB", "894"));
		locations.add(new Location("ZIMBABWE", "ZW", "ZWE", "716"));
		locations.add(new Location("JERSEY", "JE", "JEY", "832"));
		locations.add(new Location("GUERNSEY", "GG", "GGY", "831"));
		locations.add(new Location("ISLE OF MAN", "IM", "IMN", "833"));
		locations.add(new Location("SERBIA", "RS", "SRB", "688"));
		locations.add(new Location("MONTENEGRO", "ME", "MNE", "499"));

	}
}
