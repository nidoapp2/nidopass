package com.nidoapp.nidopub.service.geo

import groovy.json.JsonSlurper

/**
 * Conjunto de servicios que realizan operaciones asociadas con los prefijos telefonicos de pais
 * @author Developer
 *
 */
class CountryDialingService {
	
	def codes = generateDefaultCodes()

    /**
     * Servicio que obtiene el codigo de prefijo de un pais
     * @param countryIsoCode codigo ISO del pais
     * @return prefijo
     */
    def getDialCode(countryIsoCode) {
		
		codes."${countryIsoCode}"

    }
	
	/**
	 * Servicio que obtiene la lista completa de prefijos
	 * @return
	 */
	def getDialCodes() {
		
		codes

	}
	
	/**
	 * Metodo privado que carga los prefijos de paises por defecto.
	 * @return
	 */
	def private generateDefaultCodes()
	{
		
		log.info "Estableciendo codigos..."
		
		
		codes = [:]
		
		codes.VU = '678'
		codes.VN = '84'
		codes.EC = '593'
		codes.VI = '1340'
		codes.DZ = '213'
		codes.VG = '1284'
		codes.VE = '58'
		codes.DM = '1767'
		codes.VC = '1784'
		codes.DO = '1809'
		codes.VA = '39'
		codes.DE = '49'
		codes.UZ = '998'
		codes.UY = '598'
		codes.DK = '45'
		codes.DJ = '253'
		codes.US = '1'
		codes.UG = '256'
		codes.UA = '380'
		codes.ET = '251'
		codes.ES = '34'
		codes.ER = '291'
		codes.EG = '20'
		codes.TZ = '255'
		codes.EE = '372'
		codes.TT = '1868'
		codes.TW = '886'
		codes.TV = '688'
		codes.GD = '1473'
		codes.GE = '995'
		codes.GA = '241'
		codes.GB = '44'
		codes.FR = '33'
		codes.FO = '298'
		codes.FK = '500'
		codes.FJ = '679'
		codes.FM = '691'
		codes.FI = '358'
		codes.WS = '685'
		codes.GY = '592'
		codes.GW = '245'
		codes.GU = '1671'
		codes.GT = '502'
		codes.GR = '30'
		codes.GQ = '240'
		codes.WF = '681'
		codes.GN = '224'
		codes.GM = '220'
		codes.GL = '299'
		codes.GI = '350'
		codes.GH = '233'
		codes.GG = '44'
		codes.RO = '40'
		codes.AT = '43'
		codes.AS = '1684'
		codes.AR = '54'
		codes.AQ = '672'
		codes.QA = '974'
		codes.AW = '297'
		codes.AU = '61'
		codes.AZ = '994'
		codes.BA = '387'
		codes.PT = '351'
		codes.AD = '376'
		codes.PW = '680'
		codes.AG = '1268'
		codes.AE = '971'
		codes.PR = '1'
		codes.AF = '93'
		codes.PS = '970'
		codes.AL = '355'
		codes.AI = '1264'
		codes.AO = '244'
		codes.PY = '595'
		codes.AM = '374'
		codes.AN = '599'
		codes.TG = '228'
		codes.BW = '267'
		codes.TF = '569'
		codes.BV = '47'
		codes.BY = '375'
		codes.TD = '235'
		codes.BS = '1242'
		codes.TK = '690'
		codes.BR = '55'
		codes.TJ = '992'
		codes.TH = '66'
		codes.BT = '975'
		codes.TO = '676'
		codes.TN = '216'
		codes.TM = '993'
		codes.TL = '670'
		codes.CA = '1'
		codes.TR = '90'
		codes.BZ = '501'
		codes.BF = '226'
		codes.BG = '359'
		codes.SV = '503'
		codes.BH = '973'
		codes.BI = '257'
		codes.ST = '239'
		codes.BB = '1246'
		codes.SY = '963'
		codes.SZ = '268'
		codes.BD = '880'
		codes.BE = '32'
		codes.BN = '673'
		codes.BO = '591'
		codes.BJ = '229'
		codes.TC = '1649'
		codes.BL = '590'
		codes.BM = '1441'
		codes.CZ = '420'
		codes.SD = '249'
		codes.CY = '357'
		codes.SC = '248'
		codes.CX = '61'
		codes.SE = '46'
		codes.SH = '290'
		codes.CV = '238'
		codes.CU = '53'
		codes.SG = '65'
		codes.SI = '386'
		codes.SL = '232'
		codes.SK = '421'
		codes.SN = '221'
		codes.SM = '378'
		codes.SO = '252'
		codes.SR = '597'
		codes.CI = '225'
		codes.RS = '381'
		codes.CG = '242'
		codes.RU = '7'
		codes.CH = '41'
		codes.RW = '250'
		codes.CF = '236'
		codes.CC = '61'
		codes.CD = '243'
		codes.CR = '506'
		codes.CO = '57'
		codes.CM = '237'
		codes.CN = '86'
		codes.SA = '966'
		codes.CK = '682'
		codes.SB = '677'
		codes.CL = '56'
		codes.LV = '371'
		codes.LU = '352'
		codes.LT = '370'
		codes.LY = '218'
		codes.LS = '266'
		codes.LR = '231'
		codes.MG = '261'
		codes.MH = '692'
		codes.ME = '382'
		codes.MF = '1599'
		codes.MK = '389'
		codes.ML = '223'
		codes.MC = '377'
		codes.MD = '373'
		codes.MA = '212'
		codes.MV = '960'
		codes.MU = '230'
		codes.MX = '52'
		codes.MW = '265'
		codes.MZ = '258'
		codes.MY = '60'
		codes.MN = '976'
		codes.MM = '95'
		codes.MP = '1670'
		codes.MO = '853'
		codes.MR = '222'
		codes.MT = '356'
		codes.MS = '1664'
		codes.NF = '672'
		codes.NG = '234'
		codes.NI = '505'
		codes.NL = '31'
		codes.NA = '264'
		codes.NC = '687'
		codes.NE = '227'
		codes.NZ = '64'
		codes.NU = '683'
		codes.NR = '674'
		codes.NP = '977'
		codes.NO = '47'
		codes.OM = '968'
		codes.PL = '48'
		codes.PM = '508'
		codes.PN = '870'
		codes.PH = '63'
		codes.PK = '92'
		codes.PE = '51'
		codes.PF = '689'
		codes.PG = '675'
		codes.PA = '507'
		codes.HK = '852'
		codes.ZA = '27'
		codes.HN = '504'
		codes.HR = '385'
		codes.HT = '509'
		codes.HU = '36'
		codes.ZM = '260'
		codes.ID = '62'
		codes.ZW = '263'
		codes.IE = '353'
		codes.IL = '972'
		codes.IM = '44'
		codes.IN = '91'
		codes.IQ = '964'
		codes.IR = '98'
		codes.IS = '354'
		codes.YE = '967'
		codes.IT = '39'
		codes.YT = '262'
		codes.JP = '81'
		codes.JO = '962'
		codes.JM = '1876'
		codes.KI = '686'
		codes.KH = '855'
		codes.KG = '996'
		codes.KE = '254'
		codes.KP = '850'
		codes.KR = '82'
		codes.KM = '269'
		codes.KN = '1869'
		codes.KW = '965'
		codes.KY = '1345'
		codes.KZ = '7'
		codes.LA = '856'
		codes.LC = '1758'
		codes.LB = '961'
		codes.LI = '423'
		codes.LK = '94'
		
		codes
	}
	
	/**
	 * Servicio que actauliza los codigos prefijos de paises a partir de un servicio de Geognos reconocido.
	 * @return
	 */
	def updateCodes()
	{
		log.info "Actualizando codigos..."
		try
		{
			def json = new JsonSlurper().parseText( new URL( 'http://www.geognos.com/api/en/countries/info/all.json' ).text )
			
				json?.Results?.keySet().each{
					
					def value = json?.Results?."${it}".TelPref?.replaceAll("\\s","")
					
					if(value)
					{
						codes."${it}" = value
					}
							
				}
		}
		catch(Exception e)
		{
			log.error "Error actualizando codigos: " + e
		}
		
	}
	
	
}
